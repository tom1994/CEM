package io.cem.modules.cem.service.impl;

import io.cem.common.utils.CalcUtils;
import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.dao.ScoreCollectCityDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class IndexMapViewServiceImpl implements IndexMapViewService {
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
    private ScoreCollectCityDao scoreCollectDao;
    public void saveScore(String startDate, String endDate, int cityId) throws ExecutionException, InterruptedException {
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",startDate);
        param.put("ava_terminal",endDate);
        param.put("interval",cityId);
        List<RecordHourPingEntity> pings = recordHourPingService.queryDayList(param).get();
        List<RecordHourTracertEntity> tracerts = recordHourTracertService.queryDayList(param).get();
        List<ScoreEntity> pingIcmpScores = recordHourPingService.calculatePingIcmp(pings,param);
        List<ScoreEntity> pingTcpScores = recordHourPingService.calculatePingTcp(pings,param);
        List<ScoreEntity> pingUdpScores = recordHourPingService.calculatePingUdp(pings,param);
        List<ScoreEntity> tracertIcmpScores = recordHourPingService.calculateTracertIcmp(tracerts,param);
        List<ScoreEntity> tracertUdpScores = recordHourPingService.calculateTracertUdp(tracerts,param);
        List<ScoreEntity> pingScores = recordHourPingService.calculateService1(pingIcmpScores,pingTcpScores,pingUdpScores,tracertIcmpScores,tracertUdpScores);

        List<RecordHourWebPageEntity> wbePages = recordHourWebPageService.queryDayList(param).get();
        List<ScoreEntity> wbePageScores = recordHourWebPageService.calculateService3(wbePages,param);

        List<RecordHourWebVideoEntity> webVideos = recordHourWebVideoService.queryDayList(param).get();
        List<ScoreEntity> webVideoScores = recordHourWebVideoService.calculateService5(webVideos,param);

        List<RecordHourWebDownloadEntity> webdownloads = recordHourWebDownloadService.queryDayList(param).get();
        List<RecordHourFtpEntity> ftps = recordHourFtpService.queryFtp(param);
        List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webdownloads,param);
        List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftps,param);
        List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftps,param);
        List<ScoreEntity> downLoadScores = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);

        List<RecordHourGameEntity> games = recordHourGameService.queryDayList(param).get();
        List<ScoreEntity> gameScores = recordHourGameService.calculateService6(games,param);

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
        List<ScoreEntity> netScores = recordHourSlaService.calculateService2(slaTcpScore,slaUdpScore,dnsScore,dbcpScore,pppoeScore,radiusScore);

        List<ScoreEntity> allScore = new ArrayList<ScoreEntity>();

        allScore.addAll(pingScores);
        allScore.addAll(netScores);
        allScore.addAll(wbePageScores);
        allScore.addAll(webVideoScores);
        allScore.addAll(downLoadScores);
        allScore.addAll(gameScores);
        if(allScore.size()>0){
            ScoreEntity sceAllAvg = CalcUtils.getAvgScoreEntity(allScore);
            ScoreCollectCityEntity sce = new ScoreCollectCityEntity();
            sce.setScore(sceAllAvg.getScore());
            sce.setServiceType(0);
            sce.setScoreDate(DateUtils.format(startDate,1));
            sce.setCityId(cityId);
            scoreCollectDao.save(sce);
        }

    }
}
