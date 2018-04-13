package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 测试目标评分表
 */
public class ScoreCollectTargetEntity implements Serializable {
    private Integer id;
    private Double score;
    private int target;
    private Integer serviceType;
    private Date scoreDate;

    public Integer getId() {
        return id;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
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



    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
