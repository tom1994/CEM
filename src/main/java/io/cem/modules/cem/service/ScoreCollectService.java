package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectCityEntity;
import io.cem.modules.cem.entity.ScoreCollectDayEntity;
import io.cem.modules.cem.entity.ScoreCollectEntity;
import io.cem.modules.cem.entity.ScoreCollectTargetEntity;

import java.util.List;
import java.util.Map;

public interface ScoreCollectService {
    List<ScoreCollectEntity> list(Map<String, Object> map);
    List<ScoreCollectTargetEntity> getTargerScores(Map<String, Object> map);
    List<ScoreCollectDayEntity> getDayScores(Map<String, Object> map);
    List<ScoreCollectCityEntity> getCityRanking(Map<String, Object> map);

}
