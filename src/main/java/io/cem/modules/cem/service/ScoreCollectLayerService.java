package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectLayerEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScoreCollectLayerService {
    void saveScores(String stime, String etime) throws ExecutionException, InterruptedException;
    List<ScoreCollectLayerEntity> getScores(Map<String, Object> params);
}
