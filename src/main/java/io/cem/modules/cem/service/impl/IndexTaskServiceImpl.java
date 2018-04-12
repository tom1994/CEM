package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.ScoreCollectDao;
import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.cem.entity.ScoreCollectEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.IndexTaskService;
import io.cem.modules.cem.service.RecordHourWebPageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class IndexTaskServiceImpl implements IndexTaskService {
    @Autowired
    private RecordHourWebPageService recordHourWebPageService;
    @Autowired
    private ScoreCollectDao scoreCollectDao;
    public void saveWebPageScore(String [] mouths){
        List<ScoreCollectEntity> scoreCollects = new LinkedList<ScoreCollectEntity>();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        for(String m : mouths){
            param.put("",m);
            List<RecordHourWebPageEntity> wbePages = recordHourWebPageService.queryWebPage(param);
            List<ScoreEntity> wbePageScores = recordHourWebPageService.calculateService3(wbePages);
            ScoreCollectEntity sce = new ScoreCollectEntity();
            sce.setScore(wbePageScores.get(0).getScore());
            sce.setServiceType(20);
            scoreCollectDao.save(sce);
            param.remove("");
        }
    }
    public void saveWebVideoScore(Date startTime, Date endTime){}
    public void saveWebPingScore(Date startTime,Date endTime){}
    public void saveDownLoadScore(Date startTime,Date endTime){}
    public void saveGameScore(Date startTime,Date endTime){}
}
