package io.cem.modules.cem.service.impl;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.PropertiesUtils;
import io.cem.modules.cem.dao.ScoreCollectAllDao;
import io.cem.modules.cem.dao.ScoreCollectDayDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class ScoreCollectBusyIdleTimeServiceImpl implements ScoreCollectBusyIdleTimeService {
    public static Log log = LogFactory.getLog(ScoreCollectAllService.class);
    @Autowired
    private ScoreCollectDayDao scoreCollectDayDao;
    @Autowired
    private RecordHourWebPageService recordHourWebPageService;
    @Autowired
    private RecordHourWebVideoService recordHourWebVideoService;
    @Autowired
    private RecordHourPingService recordHourPingService;
    @Autowired
    private RecordHourTracertService recordHourTracertService;
    @Autowired
    private RecordHourWebDownloadService recordHourWebDownloadService;
    @Autowired
    private RecordHourFtpService recordHourFtpService;
    @Autowired
    private RecordHourGameService recordHourGameService;
    @Autowired
    private RecordHourSlaService recordHourSlaService;
    @Autowired
    private RecordHourDnsService recordHourDnsService;
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    @Autowired
    private RecordHourDhcpService recordHourDhcpService;
    @Autowired
    private RecordHourPppoeService recordHourPppoeService;
    public void saveScore(String stime, String etime,int mothedCode) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,String> proparams = getQueryParams();
        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime",proparams.get("recordTimeStartBusy"));
        param.put("terminalTime",proparams.get("recordTimeEndBusy"));

        List<ScoreEntity> busyScores = new ArrayList<ScoreEntity>();
        List<ScoreEntity> freeScores = new ArrayList<ScoreEntity>();
        log.info("开始计算忙时分数");
        log.info("开始计算忙时分数,查询开始日期，ava_start：" + param.get("ava_start"));
        log.info("开始计算忙时分数，查询结束日期，ava_terminal：" + param.get("ava_terminal"));
        log.info("开始计算忙时分数,查询开始时间，startTime：" + param.get("startTime"));
        log.info("开始计算忙时分数，查询结束时间，terminalTime：" + param.get("terminalTime"));
        busyScores = selectMethod(mothedCode,param);
        log.info("忙时分数计算结束，业务类型："+mothedCode);
        log.info("忙时分数计算结束，数量："+busyScores.size());
        log.info("开始保存忙时分数");
        saveScoreData(busyScores,2,mothedCode);
        log.info("结束保存忙时分数");
        //================闲时

        param.put("startTime",proparams.get("recordTimeStartFree"));
        param.put("terminalTime",proparams.get("recordTimeEndFree"));
        log.info("开始计算闲时分数，第一时间段");
        log.info("开始计算闲时分数，第一时间段,查询开始日期，ava_start：" + param.get("ava_start"));
        log.info("开始计算闲时分数，第一时间段，查询结束日期，ava_terminal：" + param.get("ava_terminal"));
        log.info("开始计算闲时分数，第一时间段,查询开始时间，startTime：" + param.get("startTime"));
        log.info("开始计算闲时分数，第一时间段，查询结束时间，terminalTime：" + param.get("terminalTime"));
        freeScores = selectMethod(mothedCode,param);
        log.info("结束计算闲时分数，第一时间段，业务类型："+mothedCode);
        log.info("结束计算闲时分数，第一时间段，分数值数量："+freeScores.size());
        param.put("startTime",proparams.get("recordTimeStartFree2"));
        param.put("terminalTime",proparams.get("recordTimeEndFree2"));
        log.info("开始计算闲时分数，第二时间段");
        log.info("结束计算闲时分数，第二时间段，业务类型："+mothedCode);
        log.info("开始计算闲时分数，第二时间段,查询开始日期，ava_start：" + param.get("ava_start"));
        log.info("开始计算闲时分数，第二时间段，查询结束日期，ava_terminal：" + param.get("ava_terminal"));
        log.info("开始计算闲时分数，第二时间段,查询开始时间，startTime：" + param.get("startTime"));
        log.info("开始计算闲时分数，第二时间段，查询结束时间，terminalTime：" + param.get("terminalTime"));
        freeScores.addAll(selectMethod(mothedCode,param));
        log.info("结束计算闲时分数，第二时间段，分数值数量："+freeScores.size());
        log.info("开始保存闲时分数");
        saveScoreData(freeScores,1,mothedCode);
        log.info("结束计算闲时分数");


    }
    private void saveScoreData(List<ScoreEntity> src,int interval,int serviceType ){
        for(int i=0;i<src.size();i++){
            ScoreCollectDayEntity e = new ScoreCollectDayEntity();
            ScoreEntity s = src.get(i);
            e.setScore(s.getScore());
            e.setServiceType(serviceType);
            e.setInterval(interval);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("开始保存忙闲时数据，第"+i+"条;算法返回的业务类型："+s.getServiceType()+"RecordTime:"+s.getRecordTime());
            scoreCollectDayDao.save(e);
            log.info("保存忙闲时数据成功");
        }
    }
    private List<ScoreEntity> selectMethod(int code,Map<String,Object>param){
        log.info("计算忙闲时分数，传过来的业务类型是："+code);
        List<ScoreEntity> scores = new ArrayList<ScoreEntity>();
        try{
            switch (code){
                case 1:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.connectionDayHourChart");
                    scores = recordHourDhcpService.connectionHourChart(param);
                    break;
                case 2:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.qualityDayHourChart");
                    scores = recordHourDhcpService.qualityHourChart(param);
                    break;
                case 3:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.pageDayChart");
                    scores = recordHourDhcpService.pageHourChart(param);
                    break;
                case 4:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.downloadDayHourChart");
                    scores = recordHourDhcpService.downloadHourChart(param);
                    break;
                case 5:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.videoDayChart");
                    scores = recordHourDhcpService.videoHourChart(param);
                    break;
                case 6:
                    log.info("计算忙闲时分数，调用的方法是：recordHourDhcpService.gameDayChart");
                    scores = recordHourDhcpService.gameHourChart(param);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return scores;
    }
    public List<ScoreCollectDayEntity> getScores(Map<String,Object> params){
        return scoreCollectDayDao.queryList(params);
    }
    public void delAll(){
        scoreCollectDayDao.delAll();
    }

    private Map<String,String> getQueryParams(){
        Map<String,String> rs = new HashMap<String,String>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            rs.put("recordTimeStartBusy",prop.getProperty("recordTimeStartBusy"));
            rs.put("recordTimeEndBusy",prop.getProperty("recordTimeEndBusy"));
            rs.put("recordTimeStartFree",prop.getProperty("recordTimeStartFree"));
            rs.put("recordTimeEndFree",prop.getProperty("recordTimeEndFree"));
            rs.put("recordTimeStartFree2",prop.getProperty("recordTimeStartFree2"));
            rs.put("recordTimeEndFree2",prop.getProperty("recordTimeEndFree2"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;

    }
    //下面方法废弃
    public void saveNetworkLayerScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        String recordTimeStartBusy = "";
        String recordTimeEndBusy = "";
        String recordTimeStartIdle = "";
        String recordTimeEndIdle = "";
        String recordTimeStartIdle2 = "";
        String recordTimeEndIdle2 = "";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            recordTimeStartBusy = prop.getProperty("recordTimeStartBusy");
            recordTimeEndBusy = prop.getProperty("recordTimeEndBusy");
            recordTimeStartIdle = prop.getProperty("recordTimeStartIdle");
            recordTimeEndIdle = prop.getProperty("recordTimeEndIdle");
            recordTimeStartIdle2 = prop.getProperty("recordTimeStartIdle2");
            recordTimeEndIdle2 = prop.getProperty("recordTimeEndIdle2");

        }catch (Exception e){
            e.printStackTrace();
        }

        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime",recordTimeStartBusy);
        param.put("terminalTime",recordTimeEndBusy);

        List<RecordHourSlaEntity> slas = recordHourSlaService.querySlaList(param).get();
        List<RecordHourDnsEntity> dns = recordHourDnsService.queryDnsList(param).get();
        List<RecordHourDhcpEntity> dhcps = recordHourDhcpService.queryDhcpList(param).get();
        List<RecordHourPppoeEntity> pppoes = recordHourPppoeService.queryPppoeList(param).get();
        List<RecordHourRadiusEntity> radius = recordHourRadiusService.queryRadiusList(param).get();

        List<ScoreEntity> slaTcpScore = recordHourSlaService.calculateSlaTcp(slas);
        List<ScoreEntity> slaUdpScore = recordHourSlaService.calculateSlaUdp(slas);
        List<ScoreEntity> dnsScore = recordHourSlaService.calculateDns(dns);
        List<ScoreEntity> dbcpScore = recordHourSlaService.calculateDhcp(dhcps);
        List<ScoreEntity> radiusScore = recordHourSlaService.calculateRadius(radius);
        List<ScoreEntity> pppoeScore = recordHourSlaService.calculatePppoe(pppoes);
        List<ScoreEntity> netScores = recordHourSlaService.calculateService2(slaTcpScore,slaUdpScore,dnsScore,dbcpScore,pppoeScore,radiusScore);

        for(ScoreEntity s : netScores){
            ScoreCollectDayEntity sce = new ScoreCollectDayEntity();
            sce.setScore(s.getScore());
            sce.setServiceType(1);
            sce.setInterval(2);
            String d = DateUtils.format(s.getRecordDate());
            sce.setScoreDate(DateUtils.format(d,1));
            scoreCollectDayDao.save(sce);
        }

        //========================闲时

        param.put("startTime",recordTimeStartIdle);
        param.put("terminalTime",recordTimeEndIdle);

        List<RecordHourSlaEntity> slas_idle = recordHourSlaService.querySlaList(param).get();
        List<RecordHourDnsEntity> dns_idle = recordHourDnsService.queryDnsList(param).get();
        List<RecordHourDhcpEntity> dhcps_idle = recordHourDhcpService.queryDhcpList(param).get();
        List<RecordHourPppoeEntity> pppoes_idle = recordHourPppoeService.queryPppoeList(param).get();
        List<RecordHourRadiusEntity> radius_idle = recordHourRadiusService.queryRadiusList(param).get();

        param.put("startTime",recordTimeStartIdle2);
        param.put("terminalTime",recordTimeEndIdle2);

        slas_idle.addAll(recordHourSlaService.querySlaList(param).get());
        dns_idle.addAll(recordHourDnsService.queryDnsList(param).get());
        dhcps_idle.addAll(recordHourDhcpService.queryDhcpList(param).get());
        pppoes_idle.addAll(recordHourPppoeService.queryPppoeList(param).get());
        radius_idle.addAll(recordHourRadiusService.queryRadiusList(param).get());

        List<ScoreEntity> slaTcpScore_idle = recordHourSlaService.calculateSlaTcp(slas_idle);
        List<ScoreEntity> slaUdpScore_idle = recordHourSlaService.calculateSlaUdp(slas_idle);
        List<ScoreEntity> dnsScore_idle = recordHourSlaService.calculateDns(dns_idle);
        List<ScoreEntity> dbcpScore_idle = recordHourSlaService.calculateDhcp(dhcps_idle);
        List<ScoreEntity> radiusScore_idle = recordHourSlaService.calculateRadius(radius_idle);
        List<ScoreEntity> pppoeScore_idle = recordHourSlaService.calculatePppoe(pppoes_idle);
        List<ScoreEntity> netScores_idle = recordHourSlaService.calculateService2(slaTcpScore_idle,slaUdpScore_idle,dnsScore_idle,dbcpScore_idle,pppoeScore_idle,radiusScore_idle);

        for(ScoreEntity s : netScores_idle){
            ScoreCollectDayEntity sce = new ScoreCollectDayEntity();
            sce.setScore(s.getScore());
            sce.setServiceType(1);
            sce.setInterval(1);
            String d = DateUtils.format(s.getRecordDate());
            sce.setScoreDate(DateUtils.format(d,1));
            scoreCollectDayDao.save(sce);
        }


    }
    public void saveWebPageScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        String recordTimeStartBusy = "";
        String recordTimeEndBusy = "";
        String recordTimeStartIdle = "";
        String recordTimeEndIdle = "";
        String recordTimeStartIdle2 = "";
        String recordTimeEndIdle2 = "";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            recordTimeStartBusy = prop.getProperty("recordTimeStartBusy");
            recordTimeEndBusy = prop.getProperty("recordTimeEndBusy");
            recordTimeStartIdle = prop.getProperty("recordTimeStartIdle");
            recordTimeEndIdle = prop.getProperty("recordTimeEndIdle");
            recordTimeStartIdle2 = prop.getProperty("recordTimeStartIdle2");
            recordTimeEndIdle2 = prop.getProperty("recordTimeEndIdle2");

        }catch (Exception e){
            e.printStackTrace();
        }

        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime",recordTimeStartBusy);
        param.put("terminalTime",recordTimeEndBusy);
    }
    public void saveDownLoadScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        String recordTimeStartBusy = "";
        String recordTimeEndBusy = "";
        String recordTimeStartIdle = "";
        String recordTimeEndIdle = "";
        String recordTimeStartIdle2 = "";
        String recordTimeEndIdle2 = "";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            recordTimeStartBusy = prop.getProperty("recordTimeStartBusy");
            recordTimeEndBusy = prop.getProperty("recordTimeEndBusy");
            recordTimeStartIdle = prop.getProperty("recordTimeStartIdle");
            recordTimeEndIdle = prop.getProperty("recordTimeEndIdle");
            recordTimeStartIdle2 = prop.getProperty("recordTimeStartIdle2");
            recordTimeEndIdle2 = prop.getProperty("recordTimeEndIdle2");

        }catch (Exception e){
            e.printStackTrace();
        }

        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime",recordTimeStartBusy);
        param.put("terminalTime",recordTimeEndBusy);
    }
    public void saveWebVideoScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        String recordTimeStartBusy = "";
        String recordTimeEndBusy = "";
        String recordTimeStartIdle = "";
        String recordTimeEndIdle = "";
        String recordTimeStartIdle2 = "";
        String recordTimeEndIdle2 = "";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            recordTimeStartBusy = prop.getProperty("recordTimeStartBusy");
            recordTimeEndBusy = prop.getProperty("recordTimeEndBusy");
            recordTimeStartIdle = prop.getProperty("recordTimeStartIdle");
            recordTimeEndIdle = prop.getProperty("recordTimeEndIdle");
            recordTimeStartIdle2 = prop.getProperty("recordTimeStartIdle2");
            recordTimeEndIdle2 = prop.getProperty("recordTimeEndIdle2");

        }catch (Exception e){
            e.printStackTrace();
        }

        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime",recordTimeStartBusy);
        param.put("terminalTime",recordTimeEndBusy);
    }
    public void saveGameScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        String recordTimeStartBusy = "";
        String recordTimeEndBusy = "";
        String recordTimeStartIdle = "";
        String recordTimeEndIdle = "";
        String recordTimeStartIdle2 = "";
        String recordTimeEndIdle2 = "";

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("chart.properties").getPath()));
            Properties prop = new Properties();
            prop.load(in);
            recordTimeStartBusy = prop.getProperty("recordTimeStartBusy");
            recordTimeEndBusy = prop.getProperty("recordTimeEndBusy");
            recordTimeStartIdle = prop.getProperty("recordTimeStartIdle");
            recordTimeEndIdle = prop.getProperty("recordTimeEndIdle");
            recordTimeStartIdle2 = prop.getProperty("recordTimeStartIdle2");
            recordTimeEndIdle2 = prop.getProperty("recordTimeEndIdle2");

        }catch (Exception e){
            e.printStackTrace();
        }

        //================忙时
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("record_time_start",recordTimeStartBusy);
        param.put("record_time_start",recordTimeEndBusy);
    }


}
