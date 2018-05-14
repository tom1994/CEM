package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.ScoreCollectDayEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScoreCollectBusyIdleTimeService {
    void saveScore(String stime, String ntime,int mothedCode) throws ExecutionException, InterruptedException;
    void saveNetworkLayerScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveWebPageScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveDownLoadScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveWebVideoScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    void saveGameScore(String stime, String ntime) throws ExecutionException, InterruptedException;
    List<ScoreCollectDayEntity> getScores(Map<String,Object> params);
    void delAll();
}
