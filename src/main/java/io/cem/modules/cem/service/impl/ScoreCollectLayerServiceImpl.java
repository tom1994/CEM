package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.controller.IndexController;
import io.cem.modules.cem.dao.ScoreCollectLayerDao;
import io.cem.modules.cem.entity.ScoreCollectLayerEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.RecordHourRadiusService;
import io.cem.modules.cem.service.ScoreCollectLayerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@Service
public class ScoreCollectLayerServiceImpl implements ScoreCollectLayerService {
    public static Log log=LogFactory.getLog(ScoreCollectLayerServiceImpl.class);
    @Autowired
    private RecordHourRadiusService recordHourRadiusService;
    @Autowired
    private ScoreCollectLayerDao scoreCollectLayerDao;

    @Override
    public void saveScores(String stime, String etime) throws ExecutionException, InterruptedException{
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ava_start",stime);
        params.put("ava_terminal",etime);
        params.put("service",1);
        params.put("startTime","00:00:00");
        params.put("endTime","23:00:00");
        log.info("调用方法recordHourRadiusService.diagnoseDay");
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores = recordHourRadiusService.diagnoseDay(params);
        params.put("service",2);
        log.info("传入的业务类型是："+params.get("service"));
        scores.addAll(recordHourRadiusService.diagnoseDay(params));
        params.put("service",3);
        log.info("传入的业务类型是："+params.get("service"));
        scores.addAll(recordHourRadiusService.diagnoseDay(params));
        params.put("service",4);
        log.info("传入的业务类型是："+params.get("service"));
        scores.addAll(recordHourRadiusService.diagnoseDay(params));
        params.put("service",5);
        log.info("传入的业务类型是："+params.get("service"));
        scores.addAll(recordHourRadiusService.diagnoseDay(params));
        params.put("service",6);
        log.info("传入的业务类型是："+params.get("service"));
        scores.addAll(recordHourRadiusService.diagnoseDay(params));

        for(ScoreEntity e:scores){
            ScoreCollectLayerEntity c = new ScoreCollectLayerEntity();
            c.setScore(e.getScore());
            log.info("计算分数后获得的层级编号为："+e.getAccessLayer());
            c.setAccessLayer(e.getAccessLayer());
            c.setScoreDate(e.getRecordDate());
            c.setScoreTime(e.getRecordTime());
            c.setServiceType(e.getServiceType());
            log.info("计算分数后获得的业务类型为："+e.getAccessLayer());
            log.info("保存数据库开始 score_collect_layer ："+c);
            scoreCollectLayerDao.save(c);

        }

    }
    @Override
    public List<ScoreCollectLayerEntity> getScores(Map<String,Object> params){
        return scoreCollectLayerDao.queryList(params);
    }
}
