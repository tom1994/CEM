package io.cem.modules.cem.service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IndexHistogramViewService {

    void saveWebPageScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;

    void saveWebVideoScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;

    void saveConnectivityScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;

    void saveDownLoadScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;

    void saveGameScore(List<Map<String,String>> mouths) throws ExecutionException, InterruptedException;
}
