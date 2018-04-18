package io.cem.modules.cem.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IndexHistogramViewService {

    void saveWebPageScore(List<Date> mouths) throws ExecutionException, InterruptedException;

    void saveWebVideoScore(List<Date> mouths) throws ExecutionException, InterruptedException;

    void saveConnectivityScore(List<Date> mouths) throws ExecutionException, InterruptedException;

    void saveDownLoadScore(List<Date> mouths) throws ExecutionException, InterruptedException;

    void saveGameScore(List<Date> mouths) throws ExecutionException, InterruptedException;

    void saveNetworkLayerScore(List<Date> mouths) throws ExecutionException, InterruptedException;
}
