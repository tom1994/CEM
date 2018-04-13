package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 评分日报表
 */
public class ScoreCollectDayEntity  implements Serializable {
    private Integer id;
    private Double score;
    private Integer interval;
    private Integer serviceType;
    private Date scoreDate;

    public Integer getId() {
        return id;
    }



    public Integer getInterval() {
        return interval;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }
}
