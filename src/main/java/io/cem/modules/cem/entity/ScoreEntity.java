package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScoreEntity {
    //记录ID
    private Integer id;
    //地市
    private Integer cityId;
    //区县
    private Integer countyId;
    //探针ID
    private Integer probeId;
    //子业务类型
    private Integer serviceType;
    //测试目标ID
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
     * 设置：地市
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    /**
     * 获取：地市
     */
    public Integer getCityId() {
        return cityId;
    }
    /**
     * 设置：区县
     */
    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }
    /**
     * 获取：区县
     */
    public Integer getCountyId() {
        return countyId;
    }
    /**
     * 设置：探针ID
     */
    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }
    /**
     * 获取：探针ID
     */
    public Integer getProbeId() {
        return probeId;
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
     * 设置：测试目标ID
     */
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    /**
     * 获取：测试目标ID
     */
    public Integer getTargetId() {
        return targetId;
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
}
