package io.cem.modules.cem.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IndexLineViewService {
    void saveWebPageScore(List<Map<String,String>> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveWebVideoScore(List<Map<String,String>> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveConnectivityScore(List<Map<String,String>> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveDownLoadScore(List<Map<String,String>> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveGameScore(List<Map<String,String>> mouths,String layerTag) throws ExecutionException, InterruptedException;
}
