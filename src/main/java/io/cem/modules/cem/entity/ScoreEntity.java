package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScoreEntity {
    //记录ID
    private Integer id;
    //地市
    private String cityName;
    private Integer cityId;
    //区县
    private String countyName;
    private Integer countyId;
    //探针ID
    private String probeName;
    private Integer probeId;
    //子业务类型
    private Integer serviceType;
    //测试目标ID
    private String targetName;
    private Integer targetId;
    //分数
    private Double score;
    //权重
    private Double base;



    /**
     * 设置：记录ID
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：记录ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：子业务类型
     */
    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }
    /**
     * 获取：子业务类型
     */
    public Integer getServiceType() {
        return serviceType;
    }
    /**
     * 设置：分数
     */
    public Double getScore() {
        return score;
    }
    /**
     * 获取：分数
     */
    public void setScore(Double score) {
        this.score = score;
    }
    /**
     * 设置：权重
     */
    public Double getBase() {
        return base;
    }
    /**
     * 获取：权重
     */
    public void setBase(Double base) {
        this.base = base;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getProbeId() {
        return probeId;
    }

    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}
