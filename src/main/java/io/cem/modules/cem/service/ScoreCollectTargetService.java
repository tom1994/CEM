package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectTargetEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScoreCollectTargetService {
    void saveScore(String stime, String etime) throws ExecutionException, InterruptedException;
    List<ScoreCollectTargetEntity> getScores(Map<String,Object> params);
    void delAll();

}
