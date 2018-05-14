package io.cem.modules.cem.service.impl;

import com.alibaba.fastjson.JSON;
import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.controller.IndexController;
import io.cem.modules.cem.dao.ScoreCollectTargetDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ScoreCollectTargetServiceImpl implements ScoreCollectTargetService {
    public static Log log=LogFactory.getLog(IndexController.class);
    @Autowired
    private ScoreCollectTargetDao scoreCollectTargetDao;
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    public void saveScore(String stime, String etime) throws ExecutionException, InterruptedException{
        List<ScoreEntity> scoresAll = new ArrayList<ScoreEntity>();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ava_start",stime);
        params.put("ava_terminal",etime);
        params.put("service",1);
        params.put("starTime","00:00:00");
        params.put("terminalTime","16:00:00");
        log.info("开始计算门户排行分数，传入的参数为："+params.get("service"));
        log.info("业务类型 service ："+params.get("service"));
        log.info("开始日期 ava_start ："+params.get("ava_start"));
        log.info("结束日期 ava_terminal ："+params.get("ava_terminal"));
        log.info("开始时间 startTime ："+params.get("starTime"));
        log.info("结束时间 endTime ："+params.get("terminalTime"));
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores1 = recordHourRadiusService.calculateTargetHourScore(params);
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores1));
        params.put("service",2);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores2 = (recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores2));
        params.put("service",3);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores3 = (recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores3));
        params.put("service",4);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores4 = (recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores4));
        params.put("service",5);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores5 = (recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores5));
        params.put("service",6);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores6 = (recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+JSON.toJSON(scores6));
        scoresAll.addAll(scores1);
        scoresAll.addAll(scores2);
        scoresAll.addAll(scores3);
        scoresAll.addAll(scores4);
        scoresAll.addAll(scores5);
        scoresAll.addAll(scores6);

        log.info("开始计算门户排行分数结束，全部结果："+JSON.toJSON(scoresAll));
        for(ScoreEntity s : scoresAll){
            ScoreCollectTargetEntity e = new ScoreCollectTargetEntity();
            e.setScore(s.getScore());
            e.setServiceType(s.getServiceType());
            e.setTarget(s.getTargetId());
            e.setTargetName(s.getTargetName());
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("门户排行,更新数据表score_collect_target，数据为"+e);
            scoreCollectTargetDao.save(e);
            log.info("门户排行,更新数据表score_collect_target成功"+e);
        }

        log.info("全部更新数据表score_collect_target成功");

    }

    public List<ScoreCollectTargetEntity> getScores(Map<String,Object> params){
        return scoreCollectTargetDao.queryList(params);
    }

    @Override
    public void delAll(){
        scoreCollectTargetDao.delAll();
    }

}
