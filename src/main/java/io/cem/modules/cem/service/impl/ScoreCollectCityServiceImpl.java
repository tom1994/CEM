package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.ScoreCollectCityDao;
import io.cem.modules.cem.entity.ScoreCollectAllEntity;
import io.cem.modules.cem.entity.ScoreCollectCityEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.RecordHourRadiusService;
import io.cem.modules.cem.service.ScoreCollectCityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ScoreCollectCityServiceImpl implements ScoreCollectCityService {
    public static Log log=LogFactory.getLog(ScoreCollectCityServiceImpl.class);
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    @Autowired
    private ScoreCollectCityDao scoreCollectCityDao;
    public void saveScores(String stime, String etime) throws ExecutionException, InterruptedException{
        if(stime.equals("")||etime.equals("")){
            return;
        }
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put("ava_start",stime);
        param.put("ava_terminal",etime);
        param.put("startTime","00:00:00");
        param.put("terminalTime","23:00:00");
        param.put("service",1);
        log.info("开始计算各地区统计分数，调用recordHourRadiusService.calculateAreaDayScore方法");
        List<ScoreEntity> scores = recordHourRadiusService.calculateAreaDayScore(param);
        log.info("各地区统计分数计算结束，返回"+scores.size()+"条数据");
        for(ScoreEntity s : scores){
            ScoreCollectCityEntity e = new ScoreCollectCityEntity();
            e.setScore(s.getScore());
            e.setServiceType(s.getServiceType());
            e.setScoreDate(s.getRecordDate());
            e.setScoreTime(s.getRecordTime());
            e.setCityName(s.getCityName());
            e.setAreaName(s.getCountyName());
            e.setCityId(s.getCityId());
            e.setAreaId(s.getCountyId());
            log.info("要保存的数据"+e);
            scoreCollectCityDao.save(e);
            log.info("保存的数据成功");

        }
    }
    public List<ScoreCollectCityEntity> getScores(Map<String,Object> params){return scoreCollectCityDao.queryList(params);}
    @Override
    public void delAll(){
        scoreCollectCityDao.delAll();
    }
}
