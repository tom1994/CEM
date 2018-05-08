package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 测试目标评分表
 */
public class ScoreCollectCityEntity implements Serializable {

    private Integer id;

    private Double score;

    private Integer cityId;

    private String cityName;

    private Integer serviceType;

    private Date scoreDate;

    private String scoreTime;

    private String areaName;

    private int areaId;

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }



    public String getCityName() {
        return cityName;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public Date getScoreDate() {
        return scoreDate;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAreaName() {
        return areaName;
    }
    public int getAreaId() {
        return areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String toString(){
        return "score:"+getScore()+";"+
                "cityName:"+getCityName()+";"+
                "areaName:"+getAreaName()+";"+
                "scoreDate:"+getScoreDate()+";"+
                "scoreTime:"+getScoreTime()+";"+
                "serviceType:"+getServiceType()+";";
    }
}
