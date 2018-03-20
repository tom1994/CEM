package io.cem.modules.cem.entity;

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
    //记录日期
    private Date recordDate;
    //记录时间
    private String recordTime;
    //分数
    private Double score;
    //权重
    private Double base;

    private Integer accessLayer;

    private Integer fail;

    private Integer total;

    /*ping ICMP details*/
    //时延平均值
    private Double pingIcmpDelay;
    //时延标准差
    private Double pingIcmpDelayStd;
    //时延方差
    private Double pingIcmpDelayVar;
    //抖动平均值
    private Double pingIcmpJitter;
    //抖动标准差
    private Double pingIcmpJitterStd;
    //抖动方差
    private Double pingIcmpJitterVar;
    //丢包率
    private Double pingIcmpLossRate;

    /*ping TCP details*/
    //时延平均值
    private Double pingTcpDelay;
    //时延标准差
    private Double pingTcpDelayStd;
    //时延方差
    private Double pingTcpDelayVar;
    //抖动平均值
    private Double pingTcpJitter;
    //抖动标准差
    private Double pingTcpJitterStd;
    //抖动方差
    private Double pingTcpJitterVar;
    //丢包率
    private Double pingTcpLossRate;

    /*ping UDP details*/
    //时延平均值
    private Double pingUdpDelay;
    //时延标准差
    private Double pingUdpDelayStd;
    //时延方差
    private Double pingUdpDelayVar;
    //抖动平均值
    private Double pingUdpJitter;
    //抖动标准差
    private Double pingUdpJitterStd;
    //抖动方差
    private Double pingUdpJitterVar;
    //丢包率
    private Double pingUdpLossRate;

    /*tracert route ICMP details*/
    //时延平均值
    private Double tracertIcmpDelay;
    //时延标准差
    private Double tracertIcmpDelayStd;
    //时延方差
    private Double tracertIcmpDelayVar;
    //抖动平均值
    private Double tracertIcmpJitter;
    //抖动标准差
    private Double tracertIcmpJitterStd;
    //抖动方差
    private Double tracertIcmpJitterVar;
    //丢包率
    private Double tracertIcmpLossRate;

    /*tracert route TCP details*/
    //时延平均值
    private Double tracertTcpDelay;
    //时延标准差
    private Double tracertTcpDelayStd;
    //时延方差
    private Double tracertTcpDelayVar;
    //抖动平均值
    private Double tracertTcpJitter;
    //抖动标准差
    private Double tracertTcpJitterStd;
    //抖动方差
    private Double tracertTcpJitterVar;
    //丢包率
    private Double tracertTcpLossRate;

    /*sla tcp details*/
    //时延平均值
    private Double slaTcpDelay;
    //往向时延
    private Double slaTcpGDelay;
    //返向时延
    private Double slaTcpRDelay;
    //抖动平均值
    private Double slaTcpJitter;
    //往向抖动
    private Double slaTcpGJitter;
    //返向抖动
    private Double slaTcpRJitter;
    //丢包率
    private Double slaTcpLossRate;

    /*sla UDP details*/
    //时延平均值
    private Double slaUdpDelay;
    //往向时延
    private Double slaUdpGDelay;
    //返向时延
    private Double slaUdpRDelay;
    //抖动平均值
    private Double slaUdpJitter;
    //往向抖动
    private Double slaUdpGJitter;
    //返向抖动
    private Double slaUdpRJitter;
    //丢包率
    private Double slaUdpLossRate;

    /*dns details*/
    //时延平均值
    private Double dnsDelay;
    //查询成功率
    private Double dnsSuccessRate;

    /*dhcp details*/
    //时延平均值
    private Double dhcpDelay;
    //查询成功率
    private Double dhcpSuccessRate;

    /*pppoe details*/
    //时延平均值
    private Double pppoeDelay;
    //掉线率
    private Double pppoeDropRate;
    //查询成功率
    private Double pppoeSuccessRate;

    /*radius details*/
    //时延平均值
    private Double radiusDelay;
    //认证成功率
    private Double radiusSuccessRate;










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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getAccessLayer() {
        return accessLayer;
    }

    public void setAccessLayer(Integer accessLayer) {
        this.accessLayer = accessLayer;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getPingIcmpDelay() {
        return pingIcmpDelay;
    }

    public void setPingIcmpDelay(Double pingIcmpDelay) {
        this.pingIcmpDelay = pingIcmpDelay;
    }

    public Double getPingIcmpDelayStd() {
        return pingIcmpDelayStd;
    }

    public void setPingIcmpDelayStd(Double pingIcmpDelayStd) {
        this.pingIcmpDelayStd = pingIcmpDelayStd;
    }

    public Double getPingIcmpDelayVar() {
        return pingIcmpDelayVar;
    }

    public void setPingIcmpDelayVar(Double pingIcmpDelayVar) {
        this.pingIcmpDelayVar = pingIcmpDelayVar;
    }

    public Double getPingIcmpJitter() {
        return pingIcmpJitter;
    }

    public void setPingIcmpJitter(Double pingIcmpJitter) {
        this.pingIcmpJitter = pingIcmpJitter;
    }

    public Double getPingIcmpJitterStd() {
        return pingIcmpJitterStd;
    }

    public void setPingIcmpJitterStd(Double pingIcmpJitterStd) {
        this.pingIcmpJitterStd = pingIcmpJitterStd;
    }

    public Double getPingIcmpJitterVar() {
        return pingIcmpJitterVar;
    }

    public void setPingIcmpJitterVar(Double pingIcmpJitterVar) {
        this.pingIcmpJitterVar = pingIcmpJitterVar;
    }

    public Double getPingIcmpLossRate() {
        return pingIcmpLossRate;
    }

    public void setPingIcmpLossRate(Double pingIcmpLossRate) {
        this.pingIcmpLossRate = pingIcmpLossRate;
    }

    public Double getPingTcpDelay() {
        return pingTcpDelay;
    }

    public void setPingTcpDelay(Double pingTcpDelay) {
        this.pingTcpDelay = pingTcpDelay;
    }

    public Double getPingTcpDelayStd() {
        return pingTcpDelayStd;
    }

    public void setPingTcpDelayStd(Double pingTcpDelayStd) {
        this.pingTcpDelayStd = pingTcpDelayStd;
    }

    public Double getPingTcpDelayVar() {
        return pingTcpDelayVar;
    }

    public void setPingTcpDelayVar(Double pingTcpDelayVar) {
        this.pingTcpDelayVar = pingTcpDelayVar;
    }

    public Double getPingTcpJitter() {
        return pingTcpJitter;
    }

    public void setPingTcpJitter(Double pingTcpJitter) {
        this.pingTcpJitter = pingTcpJitter;
    }

    public Double getPingTcpJitterStd() {
        return pingTcpJitterStd;
    }

    public void setPingTcpJitterStd(Double pingTcpJitterStd) {
        this.pingTcpJitterStd = pingTcpJitterStd;
    }

    public Double getPingTcpJitterVar() {
        return pingTcpJitterVar;
    }

    public void setPingTcpJitterVar(Double pingTcpJitterVar) {
        this.pingTcpJitterVar = pingTcpJitterVar;
    }

    public Double getPingTcpLossRate() {
        return pingTcpLossRate;
    }

    public void setPingTcpLossRate(Double pingTcpLossRate) {
        this.pingTcpLossRate = pingTcpLossRate;
    }

    public Double getPingUdpDelay() {
        return pingUdpDelay;
    }

    public void setPingUdpDelay(Double pingUdpDelay) {
        this.pingUdpDelay = pingUdpDelay;
    }

    public Double getPingUdpDelayStd() {
        return pingUdpDelayStd;
    }

    public void setPingUdpDelayStd(Double pingUdpDelayStd) {
        this.pingUdpDelayStd = pingUdpDelayStd;
    }

    public Double getPingUdpDelayVar() {
        return pingUdpDelayVar;
    }

    public void setPingUdpDelayVar(Double pingUdpDelayVar) {
        this.pingUdpDelayVar = pingUdpDelayVar;
    }

    public Double getPingUdpJitter() {
        return pingUdpJitter;
    }

    public void setPingUdpJitter(Double pingUdpJitter) {
        this.pingUdpJitter = pingUdpJitter;
    }

    public Double getPingUdpJitterStd() {
        return pingUdpJitterStd;
    }

    public void setPingUdpJitterStd(Double pingUdpJitterStd) {
        this.pingUdpJitterStd = pingUdpJitterStd;
    }

    public Double getPingUdpJitterVar() {
        return pingUdpJitterVar;
    }

    public void setPingUdpJitterVar(Double pingUdpJitterVar) {
        this.pingUdpJitterVar = pingUdpJitterVar;
    }

    public Double getPingUdpLossRate() {
        return pingUdpLossRate;
    }

    public void setPingUdpLossRate(Double pingUdpLossRate) {
        this.pingUdpLossRate = pingUdpLossRate;
    }

    public Double getTracertIcmpDelay() {
        return tracertIcmpDelay;
    }

    public void setTracertIcmpDelay(Double tracertIcmpDelay) {
        this.tracertIcmpDelay = tracertIcmpDelay;
    }

    public Double getTracertIcmpDelayStd() {
        return tracertIcmpDelayStd;
    }

    public void setTracertIcmpDelayStd(Double tracertIcmpDelayStd) {
        this.tracertIcmpDelayStd = tracertIcmpDelayStd;
    }

    public Double getTracertIcmpDelayVar() {
        return tracertIcmpDelayVar;
    }

    public void setTracertIcmpDelayVar(Double tracertIcmpDelayVar) {
        this.tracertIcmpDelayVar = tracertIcmpDelayVar;
    }

    public Double getTracertIcmpJitter() {
        return tracertIcmpJitter;
    }

    public void setTracertIcmpJitter(Double tracertIcmpJitter) {
        this.tracertIcmpJitter = tracertIcmpJitter;
    }

    public Double getTracertIcmpJitterStd() {
        return tracertIcmpJitterStd;
    }

    public void setTracertIcmpJitterStd(Double tracertIcmpJitterStd) {
        this.tracertIcmpJitterStd = tracertIcmpJitterStd;
    }

    public Double getTracertIcmpJitterVar() {
        return tracertIcmpJitterVar;
    }

    public void setTracertIcmpJitterVar(Double tracertIcmpJitterVar) {
        this.tracertIcmpJitterVar = tracertIcmpJitterVar;
    }

    public Double getTracertIcmpLossRate() {
        return tracertIcmpLossRate;
    }

    public void setTracertIcmpLossRate(Double tracertIcmpLossRate) {
        this.tracertIcmpLossRate = tracertIcmpLossRate;
    }

    public Double getTracertTcpDelay() {
        return tracertTcpDelay;
    }

    public void setTracertTcpDelay(Double tracertTcpDelay) {
        this.tracertTcpDelay = tracertTcpDelay;
    }

    public Double getTracertTcpDelayStd() {
        return tracertTcpDelayStd;
    }

    public void setTracertTcpDelayStd(Double tracertTcpDelayStd) {
        this.tracertTcpDelayStd = tracertTcpDelayStd;
    }

    public Double getTracertTcpDelayVar() {
        return tracertTcpDelayVar;
    }

    public void setTracertTcpDelayVar(Double tracertTcpDelayVar) {
        this.tracertTcpDelayVar = tracertTcpDelayVar;
    }

    public Double getTracertTcpJitter() {
        return tracertTcpJitter;
    }

    public void setTracertTcpJitter(Double tracertTcpJitter) {
        this.tracertTcpJitter = tracertTcpJitter;
    }

    public Double getTracertTcpJitterStd() {
        return tracertTcpJitterStd;
    }

    public void setTracertTcpJitterStd(Double tracertTcpJitterStd) {
        this.tracertTcpJitterStd = tracertTcpJitterStd;
    }

    public Double getTracertTcpJitterVar() {
        return tracertTcpJitterVar;
    }

    public void setTracertTcpJitterVar(Double tracertTcpJitterVar) {
        this.tracertTcpJitterVar = tracertTcpJitterVar;
    }

    public Double getTracertTcpLossRate() {
        return tracertTcpLossRate;
    }

    public void setTracertTcpLossRate(Double tracertTcpLossRate) {
        this.tracertTcpLossRate = tracertTcpLossRate;
    }

    public Double getSlaTcpDelay() {
        return slaTcpDelay;
    }

    public void setSlaTcpDelay(Double slaTcpDelay) {
        this.slaTcpDelay = slaTcpDelay;
    }

    public Double getSlaTcpGDelay() {
        return slaTcpGDelay;
    }

    public void setSlaTcpGDelay(Double slaTcpGDelay) {
        this.slaTcpGDelay = slaTcpGDelay;
    }

    public Double getSlaTcpRDelay() {
        return slaTcpRDelay;
    }

    public void setSlaTcpRDelay(Double slaTcpRDelay) {
        this.slaTcpRDelay = slaTcpRDelay;
    }

    public Double getSlaTcpJitter() {
        return slaTcpJitter;
    }

    public void setSlaTcpJitter(Double slaTcpJitter) {
        this.slaTcpJitter = slaTcpJitter;
    }

    public Double getSlaTcpGJitter() {
        return slaTcpGJitter;
    }

    public void setSlaTcpGJitter(Double slaTcpGJitter) {
        this.slaTcpGJitter = slaTcpGJitter;
    }

    public Double getSlaTcpRJitter() {
        return slaTcpRJitter;
    }

    public void setSlaTcpRJitter(Double slaTcpRJitter) {
        this.slaTcpRJitter = slaTcpRJitter;
    }

    public Double getSlaTcpLossRate() {
        return slaTcpLossRate;
    }

    public void setSlaTcpLossRate(Double slaTcpLossRate) {
        this.slaTcpLossRate = slaTcpLossRate;
    }

    public Double getSlaUdpDelay() {
        return slaUdpDelay;
    }

    public void setSlaUdpDelay(Double slaUdpDelay) {
        this.slaUdpDelay = slaUdpDelay;
    }

    public Double getSlaUdpGDelay() {
        return slaUdpGDelay;
    }

    public void setSlaUdpGDelay(Double slaUdpGDelay) {
        this.slaUdpGDelay = slaUdpGDelay;
    }

    public Double getSlaUdpRDelay() {
        return slaUdpRDelay;
    }

    public void setSlaUdpRDelay(Double slaUdpRDelay) {
        this.slaUdpRDelay = slaUdpRDelay;
    }

    public Double getSlaUdpJitter() {
        return slaUdpJitter;
    }

    public void setSlaUdpJitter(Double slaUdpJitter) {
        this.slaUdpJitter = slaUdpJitter;
    }

    public Double getSlaUdpGJitter() {
        return slaUdpGJitter;
    }

    public void setSlaUdpGJitter(Double slaUdpGJitter) {
        this.slaUdpGJitter = slaUdpGJitter;
    }

    public Double getSlaUdpRJitter() {
        return slaUdpRJitter;
    }

    public void setSlaUdpRJitter(Double slaUdpRJitter) {
        this.slaUdpRJitter = slaUdpRJitter;
    }

    public Double getSlaUdpLossRate() {
        return slaUdpLossRate;
    }

    public void setSlaUdpLossRate(Double slaUdpLossRate) {
        this.slaUdpLossRate = slaUdpLossRate;
    }

    public Double getDnsDelay() {
        return dnsDelay;
    }

    public void setDnsDelay(Double dnsDelay) {
        this.dnsDelay = dnsDelay;
    }

    public Double getDnsSuccessRate() {
        return dnsSuccessRate;
    }

    public void setDnsSuccessRate(Double dnsSuccessRate) {
        this.dnsSuccessRate = dnsSuccessRate;
    }

    public Double getDhcpDelay() {
        return dhcpDelay;
    }

    public void setDhcpDelay(Double dhcpDelay) {
        this.dhcpDelay = dhcpDelay;
    }

    public Double getDhcpSuccessRate() {
        return dhcpSuccessRate;
    }

    public void setDhcpSuccessRate(Double dhcpSuccessRate) {
        this.dhcpSuccessRate = dhcpSuccessRate;
    }

    public Double getPppoeDelay() {
        return pppoeDelay;
    }

    public void setPppoeDelay(Double pppoeDelay) {
        this.pppoeDelay = pppoeDelay;
    }

    public Double getPppoeDropRate() {
        return pppoeDropRate;
    }

    public void setPppoeDropRate(Double pppoeDropRate) {
        this.pppoeDropRate = pppoeDropRate;
    }

    public Double getPppoeSuccessRate() {
        return pppoeSuccessRate;
    }

    public void setPppoeSuccessRate(Double pppoeSuccessRate) {
        this.pppoeSuccessRate = pppoeSuccessRate;
    }

    public Double getRadiusDelay() {
        return radiusDelay;
    }

    public void setRadiusDelay(Double radiusDelay) {
        this.radiusDelay = radiusDelay;
    }

    public Double getRadiusSuccessRate() {
        return radiusSuccessRate;
    }

    public void setRadiusSuccessRate(Double radiusSuccessRate) {
        this.radiusSuccessRate = radiusSuccessRate;
    }
}
