package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectAllEntity;
import io.cem.modules.cem.entity.ScoreCollectEntity;
import io.cem.modules.cem.entity.ScoreEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScoreCollectAllService {
    List<ScoreEntity> saveConnectivityScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveNetworkLayerScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveWebPageScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveDownLoadScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveWebVideoScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveGameScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    List<ScoreCollectAllEntity> getScores(Map<String, Object> params);
    List<ScoreCollectAllEntity> getQoeScores(Map<String, Object> params);
    List<ScoreCollectAllEntity> getAreaScores(Map<String, Object> params);
    List<ScoreCollectAllEntity> getSeniorityScores(Map<String, Object> params);
    List<ScoreCollectAllEntity> getIntervalScores(Map<String, Object> params);

}
