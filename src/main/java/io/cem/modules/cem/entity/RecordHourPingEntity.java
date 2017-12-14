package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
public class RecordHourPingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//记录ID
	private Integer id;
	//地市
	private Integer cityId;
	//区县
	private Integer countyId;
	//探针ID
	private Integer probeId;
	//探针端口
	private String port;
	//任务ID
	private Integer taskId;
	//子业务类型
	private Integer serviceType;
	//测试目标ID
	private Integer targetId;
	//测试目标IP
	private Integer targetIp;
	//记录日期
	private Date recordDate;
	//记录时间
	private String recordTime;
	//时延平均值
	private Double delay;
	//时延标准差
	private Double delayStd;
	//时延方差
	private Double delayVar;
	//抖动平均值
	private Double jitter;
	//抖动标准差
	private Double jitterStd;
	//抖动方差
	private Double jitterVar;
	//丢包率
	private Double lossRate;
	//备注
	private String remark;
	//地市名称
	private String cityName;
	//区县名称
	private String areaName;
	//探针名称
	private String probeName;
	//测试目标名称
	private String targetName;

	private Integer accessLayer;

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
	 * 设置：探针端口
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * 获取：探针端口
	 */
	public String getPort() {
		return port;
	}
	/**
	 * 设置：任务ID
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：任务ID
	 */
	public Integer getTaskId() {
		return taskId;
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
	 * 设置：测试目标IP
	 */
	public void setTargetIp(Integer targetIp) {
		this.targetIp = targetIp;
	}
	/**
	 * 获取：测试目标IP
	 */
	public Integer getTargetIp() {
		return targetIp;
	}
	/**
	 * 设置：记录日期
	 */
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	/**
	 * 获取：记录日期
	 */
	public Date getRecordDate() {
		return recordDate;
	}
	/**
	 * 设置：记录时间
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	/**
	 * 获取：记录时间
	 */
	public String getRecordTime() {
		return recordTime;
	}
	/**
	 * 设置：时延平均值
	 */
	public void setDelay(Double delay) {
		this.delay = delay;
	}
	/**
	 * 获取：时延平均值
	 */
	public Double getDelay() {
		return delay;
	}
	/**
	 * 设置：时延标准差
	 */
	public void setDelayStd(Double delayStd) {
		this.delayStd = delayStd;
	}
	/**
	 * 获取：时延标准差
	 */
	public Double getDelayStd() {
		return delayStd;
	}
	/**
	 * 设置：时延方差
	 */
	public void setDelayVar(Double delayVar) {
		this.delayVar = delayVar;
	}
	/**
	 * 获取：时延方差
	 */
	public Double getDelayVar() {
		return delayVar;
	}
	/**
	 * 设置：抖动平均值
	 */
	public void setJitter(Double jitter) {
		this.jitter = jitter;
	}
	/**
	 * 获取：抖动平均值
	 */
	public Double getJitter() {
		return jitter;
	}
	/**
	 * 设置：抖动标准差
	 */
	public void setJitterStd(Double jitterStd) {
		this.jitterStd = jitterStd;
	}
	/**
	 * 获取：抖动标准差
	 */
	public Double getJitterStd() {
		return jitterStd;
	}
	/**
	 * 设置：抖动方差
	 */
	public void setJitterVar(Double jitterVar) {
		this.jitterVar = jitterVar;
	}
	/**
	 * 获取：抖动方差
	 */
	public Double getJitterVar() {
		return jitterVar;
	}
	/**
	 * 设置：丢包率
	 */
	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}
	/**
	 * 获取：丢包率
	 */
	public Double getLossRate() {
		return lossRate;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public Integer getAccessLayer() {
		return accessLayer;
	}

	public void setAccessLayer(Integer accessLayer) {
		this.accessLayer = accessLayer;
	}
}
