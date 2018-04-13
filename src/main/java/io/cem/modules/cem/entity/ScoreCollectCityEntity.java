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

    private Integer cityiId;

    private String cityiName;

    private Integer serviceType;

    private Date scoreDate;

    public Integer getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getCityiId() {
        return cityiId;
    }

    public String getCityiName() {
        return cityiName;
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

    public void setCityiName(String cityiName) {
        this.cityiName = cityiName;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public void setCityiId(Integer cityiId) {
        this.cityiId = cityiId;
    }
}
