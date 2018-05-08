package io.cem.modules.cem.entity;

import java.util.Date;

public class ScoreCollectAllEntity {
    private Integer id;
    private Double score;
    private Integer serviceType;
    private Date scoreDate;
    private String target;
    private String cityName;
    private String areaName;
    private Integer accessLayer;
    private String scoreTime;

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getScoreTime() {
        return scoreTime;
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

    public void setTarget(String target) {
        this.target = target;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }



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

    public String getTarget() {
        return target;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public Integer getAccessLayer() {
        return accessLayer;
    }

    public void setAccessLayer(Integer accessLayer) {
        this.accessLayer = accessLayer;
    }

    public String toString(){
        return "id:"+id+
                ";score:"+score+
                ";scoreDate:"+scoreDate+
                ";scoreTime:"+scoreTime+
                ";serviceType:"+serviceType;


    }
}
