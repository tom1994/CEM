package io.cem.modules.cem.service.impl;

import io.cem.common.utils.CalcUtils;
import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.dao.ScoreCollectTargetDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class IndexRankingViewServiceImpl implements IndexRankingViewService {
    public static Log log=LogFactory.getLog(IndexHistogramViewServiceImpl.class);
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
    @Autowired
    private ScoreCollectTargetDao scoreCollectDao;
    public void saveConnectivityScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("target_id",target);
        List<RecordHourPingEntity> pings = recordHourPingService.queryDayList(param).get();
        List<RecordHourTracertEntity> tracerts = recordHourTracertService.queryDayList(param).get();
        List<ScoreEntity> pingIcmpScores = recordHourPingService.calculatePingIcmp(pings,param);
        List<ScoreEntity> pingTcpScores = recordHourPingService.calculatePingTcp(pings,param);
        List<ScoreEntity> pingUdpScores = recordHourPingService.calculatePingUdp(pings,param);

        List<ScoreEntity> tracertIcmpScores = recordHourPingService.calculateTracertIcmp(tracerts,param);
        List<ScoreEntity> tracertUdpScores = recordHourPingService.calculateTracertUdp(tracerts,param);

        List<ScoreEntity> pingScores = recordHourPingService.calculateService1(pingIcmpScores,pingTcpScores,pingUdpScores,tracertIcmpScores,tracertUdpScores);
        if(pingScores.size()>0){
            ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
            sce.setScore(pingScores.get(0).getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setTarget(target);
            scoreCollectDao.save(sce);
        }
    }
    public void saveNetworkLayerScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));
            List<RecordHourSlaEntity> slas = recordHourSlaService.queryDayList(param).get();
            List<RecordHourDnsEntity> dns = recordHourDnsService.queryDayList(param).get();
            List<RecordHourDhcpEntity> dhcps = recordHourDhcpService.queryDayList(param).get();
            List<RecordHourPppoeEntity> pppoes = recordHourPppoeService.queryDayList(param).get();
            List<RecordHourRadiusEntity> radius = recordHourRadiusService.queryDayList(param).get();

            List<ScoreEntity> slaTcpScore = recordHourSlaService.calculateSlaTcp(slas,param);
            List<ScoreEntity> slaUdpScore = recordHourSlaService.calculateSlaUdp(slas,param);
            List<ScoreEntity> dnsScore = recordHourSlaService.calculateDns(dns,param);
            List<ScoreEntity> dbcpScore = recordHourSlaService.calculateDhcp(dhcps,param);
            List<ScoreEntity> radiusScore = recordHourSlaService.calculateRadius(radius,param);
            List<ScoreEntity> pppoeScore = recordHourSlaService.calculatePppoe(pppoes,param);
//List<ScoreEntity> calculateService2(List<ScoreEntity> slaTcp,List<ScoreEntity> slaUdp,List<ScoreEntity> dns,List<ScoreEntity> dhcp,List<ScoreEntity> pppoe,List<ScoreEntity> radius);
            List<ScoreEntity> netScores = recordHourSlaService.calculateService2(slaTcpScore,slaUdpScore,dnsScore,dbcpScore,pppoeScore,radiusScore);
            if(netScores.size()>0){
                ScoreEntity sceAllAvg = CalcUtils.getAvgScoreEntity(netScores);

                ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
                sce.setScore(sceAllAvg.getScore());
                sce.setServiceType(1);
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                scoreCollectDao.save(sce);
            }


        }
    }
    public void saveWebPageScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("target_id",target);
        List<RecordHourWebPageEntity> wbePages = recordHourWebPageService.queryDayList(param).get();
        List<ScoreEntity> wbePageScores = recordHourWebPageService.calculateService3(wbePages,param);
        if(wbePageScores.size()>0){
            ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
            sce.setScore(wbePageScores.get(0).getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setTarget(target);
            scoreCollectDao.save(sce);
        }
    }
    public void saveWebVideoScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("target_id",target);
        List<RecordHourWebVideoEntity> webVideos = recordHourWebVideoService.queryDayList(param).get();
        List<ScoreEntity> webVideoScores = recordHourWebVideoService.calculateService5(webVideos,param);
        if(webVideoScores.size()>0){
            ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
            sce.setScore(webVideoScores.get(0).getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setTarget(target);
            scoreCollectDao.save(sce);
        }
    }

    public void saveDownLoadScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("target_id",target);
        List<RecordHourWebDownloadEntity> webdownloads = recordHourWebDownloadService.queryDayList(param).get();
        List<RecordHourFtpEntity> ftps = recordHourFtpService.queryFtp(param);
        List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webdownloads,param);
        List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftps,param);
        List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftps,param);
        List<ScoreEntity> downLoadScores = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
        if(downLoadScores.size()>0){
            ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
            sce.setScore(downLoadScores.get(0).getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setTarget(target);
            scoreCollectDao.save(sce);
        }

    }
    public void saveGameScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("target_id",target);

        List<RecordHourGameEntity> games = recordHourGameService.queryDayList(param).get();
        List<ScoreEntity> gameScores = recordHourGameService.calculateService6(games,param);

        if(gameScores.size()>0){
            ScoreCollectTargetEntity sce = new ScoreCollectTargetEntity();
            sce.setScore(gameScores.get(0).getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setTarget(target);
            scoreCollectDao.save(sce);
        }
    }
}
