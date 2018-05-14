package io.cem.modules.cem.service.impl;

import com.alibaba.fastjson.JSON;
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

import java.util.ArrayList;
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
        List<ScoreEntity> scoresAll = new ArrayList<ScoreEntity>();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ava_start",stime);
        params.put("ava_terminal",etime);
        params.put("service",1);
        params.put("startTime","00:00:00");
        params.put("endTime","23:00:00");
        log.info("调用方法recordHourRadiusService.diagnoseDay");
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores1 = recordHourRadiusService.diagnoseDay(params);
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores1));
        params.put("service",2);
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores2 = (recordHourRadiusService.diagnoseDay(params));
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores2));
        params.put("service",3);
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores3 = (recordHourRadiusService.diagnoseDay(params));
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores3));
        params.put("service",4);
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores4 = (recordHourRadiusService.diagnoseDay(params));
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores4));
        params.put("service",5);
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores5 = (recordHourRadiusService.diagnoseDay(params));
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores5));
        params.put("service",6);
        log.info("传入的业务类型是："+params.get("service"));
        List<ScoreEntity> scores6 = recordHourRadiusService.diagnoseDay(params);
        log.info("recordHourRadiusService.diagnoseDay计算返回的结果："+JSON.toJSON(scores6));
        scoresAll.addAll(scores1);
        scoresAll.addAll(scores2);
        scoresAll.addAll(scores3);
        scoresAll.addAll(scores4);
        scoresAll.addAll(scores5);
        scoresAll.addAll(scores6);
        log.info("合并后的结果："+JSON.toJSON(scoresAll));
        for(ScoreEntity e:scoresAll){
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
    @Override
    public void delAll(){
        scoreCollectLayerDao.delAll();
    }
}
