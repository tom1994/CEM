package io.cem.modules.cem.service.impl;

import io.cem.common.utils.DateUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.dao.ScoreCollectAllDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@Service
public class ScoreCollectAllServiceImpl implements ScoreCollectAllService {
    public static Log log = LogFactory.getLog(ScoreCollectAllService.class);
    @Autowired
    private ScoreCollectAllDao scoreCollectAllDao;

    //new autowired
    @Autowired
    private RecordHourDhcpService recordHourDhcpService;


    @Override
    public List<ScoreEntity>  saveConnectivityScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return null;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");

        log.info("网络连通性，月度统计，调用方法：recordHourDhcpService.connectionDayHourChart");
        List<ScoreEntity> pingScores = recordHourDhcpService.connectionDayChart(param);
        log.info("网络连通性，月度统计，计算后得分："+pingScores);
        for(ScoreEntity s : pingScores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(1);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("更新数据库表成功：");
        return pingScores;

    }
    public void saveNetworkLayerScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        log.info("网络层质量，月度统计，调用方法：recordHourDhcpService.qualityDayHourChart");
        List<ScoreEntity> scores = recordHourDhcpService.qualityDayChart(param);
        log.info("网络层质量，月度统计，计算后得分："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(2);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("网络层质量，开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("网络层质量，更新数据库表成功：");


    }
    @Override
    public void saveWebPageScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        log.info("网页浏览，月度统计，调用方法：recordHourDhcpService.pageDayChart");
        List<ScoreEntity> scores = recordHourDhcpService.pageDayChart(param);
        log.info("网页浏览，月度统计，计算后得分："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(3);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("网页浏览，开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("网页浏览，更新数据库表成功：");
    }
    @Override
    public void saveDownLoadScore(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        log.info("文件下载，调用方法recordHourDhcpService.downloadDayHourChart");
        List<ScoreEntity> scores = recordHourDhcpService.downloadDayChart(param);
        log.info("文件下载，月度统计，计算后得分："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(4);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("文件下载，开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("文件下载，更新数据库表成功：");
    }


    @Override
    public void saveWebVideoScore(String stime, String etime) throws ExecutionException, InterruptedException{

        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        log.info("在线视频，月度统计，调用方法：recordHourDhcpService.videoDayChart");
        List<ScoreEntity> scores = recordHourDhcpService.videoDayChart(param);
        log.info("在线视频，月度统计，计算后得分："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(5);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("在线视频，开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("在线视频，更新数据库表成功");
    }

    @Override
    public void saveGameScore(String stime, String etime) throws ExecutionException, InterruptedException {
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        log.info("网络游戏，月度统计，调用方法：recordHourDhcpService.gameDayChart");
        List<ScoreEntity> scores = recordHourDhcpService.gameDayChart(param);
        log.info("网络游戏，月度统计，计算后得分："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectAllEntity e = new ScoreCollectAllEntity();
            e.setScore(s.getScore());
            e.setServiceType(6);
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("网络游戏，开始更新数据库表，数据为："+e);
            scoreCollectAllDao.save(e);
        }
        log.info("网络游戏，更新数据库表成功");
    }


    public List<ScoreCollectAllEntity> getScores(Map<String,Object> params){
        return scoreCollectAllDao.queryList(params);
    }

    public void delAll(){
        scoreCollectAllDao.delAll();
    }

    //下面方法废弃
    @Override
    public List<ScoreCollectAllEntity> getQoeScores(Map<String,Object> params){
        return scoreCollectAllDao.queryListForServiceType(params);
    }
    @Override
    public List<ScoreCollectAllEntity> getAreaScores(Map<String,Object> params){
        return scoreCollectAllDao.queryListForArea(params);
    }
    @Override
    public List<ScoreCollectAllEntity> getSeniorityScores(Map<String,Object> params){
        return scoreCollectAllDao.queryListForTarget(params);
    }
    @Override
    public List<ScoreCollectAllEntity> getIntervalScores(Map<String,Object> params){
        return null;
    }

}
