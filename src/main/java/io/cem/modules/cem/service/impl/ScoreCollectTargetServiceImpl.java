package io.cem.modules.cem.service.impl;

import io.cem.common.utils.DateUtils;
import io.cem.modules.cem.controller.IndexController;
import io.cem.modules.cem.dao.ScoreCollectTargetDao;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ScoreCollectTargetServiceImpl implements ScoreCollectTargetService {
    public static Log log=LogFactory.getLog(IndexController.class);
    @Autowired
    private ScoreCollectTargetDao scoreCollectTargetDao;
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    public void saveScore(String stime, String etime) throws ExecutionException, InterruptedException{
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ava_start",stime);
        params.put("ava_terminal",etime);
        params.put("service",1);
        params.put("startTime","00:00:00");
        params.put("terminalTime","23:00:00");
        log.info("开始计算门户排行分数，传入的参数为："+params.get("service"));
        log.info("业务类型："+params.get("service"));
        log.info("开始日期："+params.get("ava_start"));
        log.info("结束日期："+params.get("ava_terminal"));
        log.info("开始时间："+params.get("startTime"));
        log.info("结束时间："+params.get("endTime"));
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        List<ScoreEntity> scores = recordHourRadiusService.calculateTargetDayScore(params);
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回计算结果为："+scores);
        params.put("service",2);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        scores.addAll(recordHourRadiusService.calculateTargetDayScore(params));
        params.put("service",3);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        scores.addAll(recordHourRadiusService.calculateTargetDayScore(params));
        params.put("service",4);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        scores.addAll(recordHourRadiusService.calculateTargetDayScore(params));
        params.put("service",5);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        scores.addAll(recordHourRadiusService.calculateTargetDayScore(params));
        params.put("service",6);
        log.info("开始调用recordHourRadiusService.calculateTargetDayScore方法，传入的业务类型为："+params.get("service"));
        scores.addAll(recordHourRadiusService.calculateTargetDayScore(params));
        log.info("结束调用recordHourRadiusService.calculateTargetDayScore方法，返回全部计算结果为："+scores);
        for(ScoreEntity s : scores){
            ScoreCollectTargetEntity e = new ScoreCollectTargetEntity();
            e.setScore(s.getScore());
            e.setServiceType(s.getServiceType());
            e.setTarget(s.getTargetId());
            e.setTargetName(s.getTargetName());
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            log.info("更新数据表score_collect_target，数据为"+e);
            scoreCollectTargetDao.save(e);
            log.info("更新数据表score_collect_target成功"+e);
        }

        log.info("全部更新数据表score_collect_target成功");

    }

    public List<ScoreCollectTargetEntity> getScores(Map<String,Object> params){
        return scoreCollectTargetDao.queryList(params);
    }

}
