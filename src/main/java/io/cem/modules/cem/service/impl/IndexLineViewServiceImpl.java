package io.cem.modules.cem.service.impl;

import io.cem.common.utils.CalcUtils;
import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.dao.ScoreCollectDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class IndexLineViewServiceImpl implements IndexLineViewService {
    public static Log log=LogFactory.getLog(IndexLineViewService.class);
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
    private ScoreCollectDao scoreCollectDao;


    public void saveConnectivityScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Date d: mouths){
            param.put("ava_start",DateUtils.setStartEndDay(d,0));
            param.put("ava_terminal",DateUtils.setStartEndDay(d,1));
            List<RecordHourPingEntity> pings = recordHourPingService.queryDayList(param).get();
            List<RecordHourTracertEntity> tracerts = recordHourTracertService.queryDayList(param).get();
            List<ScoreEntity> pingIcmpScores = recordHourPingService.calculatePingIcmp(pings);
            List<ScoreEntity> pingTcpScores = recordHourPingService.calculatePingTcp(pings);
            List<ScoreEntity> pingUdpScores = recordHourPingService.calculatePingUdp(pings);


            List<ScoreEntity> tracertIcmpScores = recordHourPingService.calculateTracertIcmp(tracerts);
            List<ScoreEntity> tracertUdpScores = recordHourPingService.calculateTracertUdp(tracerts);

            List<ScoreEntity> pingScores = recordHourPingService.calculateService1(pingIcmpScores,pingTcpScores,pingUdpScores,tracertIcmpScores,tracertUdpScores);

            for(ScoreEntity s : pingScores){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(0);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String)param.get("ava_start"),1));
                scoreCollectDao.save(sce);
            }

        }
    }

    public void saveNetworkLayerScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Date d: mouths){
            param.put("ava_start",DateUtils.setStartEndDay(d,0));
            param.put("ava_terminal",DateUtils.setStartEndDay(d,1));
            List<RecordHourSlaEntity> slas = recordHourSlaService.queryDayList(param).get();
            List<RecordHourDnsEntity> dns = recordHourDnsService.queryDayList(param).get();
            List<RecordHourDhcpEntity> dhcps = recordHourDhcpService.queryDayList(param).get();
            List<RecordHourPppoeEntity> pppoes = recordHourPppoeService.queryDayList(param).get();
            List<RecordHourRadiusEntity> radius = recordHourRadiusService.queryDayList(param).get();

            List<ScoreEntity> slaTcpScore = recordHourSlaService.calculateSlaTcp(slas);
            List<ScoreEntity> slaUdpScore = recordHourSlaService.calculateSlaUdp(slas);
            List<ScoreEntity> dnsScore = recordHourSlaService.calculateDns(dns);
            List<ScoreEntity> dbcpScore = recordHourSlaService.calculateDhcp(dhcps);
            List<ScoreEntity> radiusScore = recordHourSlaService.calculateRadius(radius);
            List<ScoreEntity> pppoeScore = recordHourSlaService.calculatePppoe(pppoes);
            List<ScoreEntity> netScores = recordHourSlaService.calculateService2(slaTcpScore,slaUdpScore,dnsScore,dbcpScore,pppoeScore,radiusScore);

            for(ScoreEntity s : netScores){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(1);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String)param.get("ava_start"),1));
                scoreCollectDao.save(sce);
            }

        }
    }
    public void saveWebPageScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Date d: mouths){
            param.put("ava_start",DateUtils.setStartEndDay(d,0));
            param.put("ava_terminal",DateUtils.setStartEndDay(d,1));
            List<RecordHourWebPageEntity> wbePages = recordHourWebPageService.queryDayList(param).get();
            List<ScoreEntity> wbePageScores = recordHourWebPageService.calculateService3(wbePages);
            for(ScoreEntity s : wbePageScores){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(2);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String)param.get("ava_start"),1));
                scoreCollectDao.save(sce);
            }


        }
    }
    public void saveDownLoadScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Date d: mouths){
            param.put("ava_start",DateUtils.setStartEndDay(d,0));
            param.put("ava_terminal",DateUtils.setStartEndDay(d,1));
            List<RecordHourWebDownloadEntity> webdownloads = recordHourWebDownloadService.queryDayList(param).get();
            List<RecordHourFtpEntity> ftps = recordHourFtpService.queryFtp(param);
            List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webdownloads);
            List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftps);
            List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftps);
            List<ScoreEntity> downLoadScores = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
            for(ScoreEntity s : downLoadScores){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(3);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String)param.get("ava_start"),1));
                scoreCollectDao.save(sce);
            }

        }
    }
    public void saveWebVideoScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Date d: mouths){
            param.put("ava_start",DateUtils.setStartEndDay(d,0));
            param.put("ava_terminal",DateUtils.setStartEndDay(d,1));
            List<RecordHourWebVideoEntity> webVideos = recordHourWebVideoService.queryDayList(param).get();
            List<ScoreEntity> webVideoScores = recordHourWebVideoService.calculateService5(webVideos);

            for(ScoreEntity s : webVideoScores){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(4);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String)param.get("ava_start"),1));
                scoreCollectDao.save(sce);
            }
        }
    }


    public void saveGameScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException {

        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        for (Date d : mouths) {
            param.put("ava_start", DateUtils.setStartEndDay(d, 0));
            param.put("ava_terminal", DateUtils.setStartEndDay(d, 1));
            List<RecordHourGameEntity> games = recordHourGameService.queryDayList(param).get();
            List<ScoreEntity> gameScores = recordHourGameService.calculateService6(games);
            for (ScoreEntity s : gameScores) {
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setServiceType(5);
                sce.setAccessLayer(s.getAccessLayer());
                sce.setScoreDate(DateUtils.format((String) param.get("ava_start"), 1));
                scoreCollectDao.save(sce);
            }


        }
    }
}
