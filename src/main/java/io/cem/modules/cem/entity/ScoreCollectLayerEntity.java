package io.cem.modules.cem.entity;

import java.util.Date;

public class ScoreCollectLayerEntity {
    private Integer id;
    private Double score;
    private Integer serviceType;
    private Date scoreDate;
    private String scoreTime;
    private Integer accessLayer;

    public Integer getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getAccessLayer() {
        return accessLayer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setAccessLayer(Integer accessLayer) {
        this.accessLayer = accessLayer;
    }

    @Override
    public String toString() {
        return "id"+id+
                "score"+score+";"+
                "serviceType"+serviceType+";"+
                "scoreDate"+scoreDate+";"+
                "scoreTime"+scoreTime+";"+
                "accessLayer"+accessLayer+";";
    }
}
