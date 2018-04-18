package io.cem.modules.cem.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IndexRankingViewService {
    void saveWebPageScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException;
    void saveWebVideoScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException;
    void saveConnectivityScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException;
    void saveDownLoadScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException;
    void saveGameScore(String startDate, String endDate, int target) throws ExecutionException, InterruptedException;
    void saveNetworkLayerScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;
}
