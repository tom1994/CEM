package io.cem.modules.cem.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IndexLineViewService {
    void saveWebPageScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveWebVideoScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveConnectivityScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveDownLoadScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveGameScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;

    void saveNetworkLayerScore(List<Date> mouths,String layerTag) throws ExecutionException, InterruptedException;
}
