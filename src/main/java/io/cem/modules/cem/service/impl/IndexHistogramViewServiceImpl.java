package io.cem.modules.cem.service.impl;

import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.dao.ScoreCollectDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class IndexHistogramViewServiceImpl implements IndexHistogramViewService {

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
    private ScoreCollectDao scoreCollectDao;


    public void saveConnectivityScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));
            List<RecordHourPingEntity> pings = recordHourPingService.queryDayList(param).get();
            List<RecordHourTracertEntity> tracerts = recordHourTracertService.queryDayList(param).get();
            List<ScoreEntity> pingIcmpScores = recordHourPingService.calculatePingIcmp(pings);
            List<ScoreEntity> pingTcpScores = recordHourPingService.calculatePingTcp(pings);
            List<ScoreEntity> pingUdpScores = recordHourPingService.calculatePingUdp(pings);

            List<ScoreEntity> tracertIcmpScores = recordHourPingService.calculateTracertIcmp(tracerts);
            List<ScoreEntity> tracertUdpScores = recordHourPingService.calculateTracertUdp(tracerts);

            List<ScoreEntity> pingScores = recordHourPingService.calculateService1(pingIcmpScores,pingTcpScores,pingUdpScores,tracertIcmpScores,tracertUdpScores);
            if(pingScores.size()>0){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setScore(pingScores.get(0).getScore());
                sce.setServiceType(0);
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                scoreCollectDao.save(sce);
            }

        }
    }

    public void saveWebPageScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            //#{start_time} AND #{terminal_time}
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));

            List<RecordHourWebPageEntity> wbePages = recordHourWebPageService.queryDayList(param).get();
            log.info("======================== wbePages ==="+wbePages);
            List<ScoreEntity> wbePageScores = recordHourWebPageService.calculateService3(wbePages);
            log.info("======================== wbePageScores ==="+wbePageScores);
            if(wbePageScores.size()>0){
                log.info("======================== update database!!!");
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setScore(wbePageScores.get(0).getScore());
                sce.setServiceType(2);
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                scoreCollectDao.save(sce);
            }

        }
    }
    public void saveDownLoadScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));
            //List<RecordHourWebDownloadEntity> queryList(Map<String, Object> map);
            List<RecordHourWebDownloadEntity> webdownloads = recordHourWebDownloadService.queryDayList(param).get();
            List<RecordHourFtpEntity> ftps = recordHourFtpService.queryFtp(param);
            List<ScoreEntity> webDownload = recordHourWebDownloadService.calculateWebDownload(webdownloads);
            List<ScoreEntity> ftpDownload = recordHourWebDownloadService.calculateFtpDownload(ftps);
            List<ScoreEntity> ftpUpload = recordHourWebDownloadService.calculateFtpUpload(ftps);
            //List<ScoreEntity> calculateService4 (List<ScoreEntity> webDownload,List<ScoreEntity> ftpDownload,List<ScoreEntity> ftpUpload);
            List<ScoreEntity> downLoadScores = recordHourWebDownloadService.calculateService4(webDownload,ftpDownload,ftpUpload);
            for(ScoreEntity s:downLoadScores){
                log.info("======================== downLoadScores ==="+s.getScore());
            }

            if(downLoadScores.size()>0){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setScore(downLoadScores.get(0).getScore());
                sce.setServiceType(3);
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                scoreCollectDao.save(sce);
            }
        }
    }
    public void saveWebVideoScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));
            List<RecordHourWebVideoEntity> webVideos = recordHourWebVideoService.queryDayList(param).get();
            List<ScoreEntity> webVideoScores = recordHourWebVideoService.calculateService5(webVideos);
            log.info("======================== webVideoScores ==="+webVideoScores);
            if(webVideoScores.size()>0){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setScore(webVideoScores.get(0).getScore());
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                sce.setServiceType(4);
                scoreCollectDao.save(sce);
            }

        }
    }


    public void saveGameScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException {

        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(Map<String,String> m : mouths){
            param.put("ava_start",m.get("startTime"));
            param.put("ava_terminal",m.get("endTime"));
            List<RecordHourGameEntity> games = recordHourGameService.queryDayList(param).get();
            List<ScoreEntity> gameScores = recordHourGameService.calculateService6(games);
            if(gameScores.size()>0){
                ScoreCollectEntity sce = new ScoreCollectEntity();
                sce.setScore(gameScores.get(0).getScore());
                sce.setScoreDate(DateUtils.format(m.get("startTime"),1));
                sce.setServiceType(5);
                scoreCollectDao.save(sce);

            }
        }
    }








}
