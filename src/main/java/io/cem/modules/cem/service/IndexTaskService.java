package io.cem.modules.cem.service;


import java.util.Date;

public interface IndexTaskService {
    void saveWebPageScore(String [] mouths);
    void saveWebVideoScore(Date startTime,Date endTime);
    void saveWebPingScore(Date startTime,Date endTime);
    void saveDownLoadScore(Date startTime,Date endTime);
    void saveGameScore(Date startTime,Date endTime);
}
