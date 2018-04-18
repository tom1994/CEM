package io.cem.modules.cem.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ScoreCollectEntity {
    private Double score;
    private int viewType;
    private int accessLayer;
    private Date scoreDate;
    private int serviceType;

    public ScoreCollectEntity() {
    }

    public Double getScore() {
        return score;
    }

    public Double getScore(Double score) {
        return this.score;
    }

    public int getViewType() {
        return viewType;
    }

    public int getAccessLayer() {
        return accessLayer;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setAccessLayer(int accessLayer) {
        this.accessLayer = accessLayer;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }
}
