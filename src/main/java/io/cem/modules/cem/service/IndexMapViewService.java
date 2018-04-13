package io.cem.modules.cem.service;

import java.util.concurrent.ExecutionException;

public interface IndexMapViewService {
    void saveScore(String startDate, String endDate, int cityId) throws ExecutionException, InterruptedException;
}
