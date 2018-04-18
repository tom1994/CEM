package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.ScoreCollectCityDao;
import io.cem.modules.cem.dao.ScoreCollectDao;
import io.cem.modules.cem.dao.ScoreCollectDayDao;
import io.cem.modules.cem.dao.ScoreCollectTargetDao;
import io.cem.modules.cem.entity.ScoreCollectCityEntity;
import io.cem.modules.cem.entity.ScoreCollectDayEntity;
import io.cem.modules.cem.entity.ScoreCollectEntity;
import io.cem.modules.cem.entity.ScoreCollectTargetEntity;
import io.cem.modules.cem.service.ScoreCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scoreCollectService")
public class ScoreCollectServiceImpl implements ScoreCollectService {
    @Autowired
    private ScoreCollectDao scoreCollectDao;
    @Autowired
    private ScoreCollectCityDao scoreCollectCityDao;
    @Autowired
    private ScoreCollectTargetDao scoreCollectTargetDao;
    @Autowired
    private ScoreCollectDayDao scoreCollectDayDao;

    @Override
    public List<ScoreCollectEntity> list(Map<String, Object> map){
        return scoreCollectDao.queryList(map);
    }
    @Override
    public List<ScoreCollectTargetEntity> getTargerScores(Map<String, Object> map){
        return scoreCollectTargetDao.queryList(map);
    }
    @Override
    public List<ScoreCollectDayEntity> getDayScores(Map<String, Object> map){
        return scoreCollectDayDao.queryList(map);
    }
    @Override
    public List<ScoreCollectCityEntity> getCityRanking(Map<String, Object> map){
        return scoreCollectCityDao.queryList(map);
    }

}
