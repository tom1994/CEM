package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectCityEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScoreCollectCityService {
    void saveScores(String stime, String ntime) throws ExecutionException, InterruptedException;
    List<ScoreCollectCityEntity> getScores(Map<String,Object> params);
    void delAll();
}
