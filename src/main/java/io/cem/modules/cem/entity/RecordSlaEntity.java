package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 */
public class RecordSlaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//测试记录ID
	private Integer id;
	//探针ID
	private Integer probeId;
	//探针端口
	private String port;
	//任务ID
	private Integer taskId;
	//子业务类型
	private Integer serviceType;
	//任务类型
	private Integer taskType;
	//测试目标类型
	private Integer targetType;
	//测试目标ID
	private Integer targetId;
	//测试目标IP
	private Long targetIp;
	//测试目标归属地
	private String targetLoc;
	//测试结果
	private Integer state;
	//记录日期
	private Date recordDate;
	//记录时间
	private String recordTime;
	//时延平均值
	private BigDecimal delay;
	//往向时延
	private BigDecimal gDelay;
	//返向时延
	private BigDecimal rDelay;
	//时延标准差
	private BigDecimal delayStd;
	//往向时延标准差
	private BigDecimal gDelayStd;
	//返向时延标准差
	private BigDecimal rDelayStd;
	//时延方差
	private BigDecimal delayVar;
	//往向时延方差
	private BigDecimal gDelayVar;
	//返向时延方差
	private BigDecimal rDelayVar;
	//抖动平均值
	private BigDecimal jitter;
	//往向抖动
	private BigDecimal gJitter;
	//返向抖动
	private BigDecimal rJitter;
	//抖动标准差
	private BigDecimal jitterStd;
	//往向抖动标准差
	private BigDecimal gJitterStd;
	//返向抖动标准差
	private BigDecimal rJitterStd;
	//抖动方差
	private BigDecimal jitterVar;
	//往向抖动方差
	private BigDecimal gJitterVar;
	//返向抖动方差
	private BigDecimal rJitterVar;
	//丢包率
	private BigDecimal lossRate;
	//往向丢包率
	private BigDecimal gLossRate;
	//返向丢包率
	private BigDecimal rLossRate;
	//备注
	private String remark;

	private String probeName;

	private String targetName;

	private String taskName;

	private String targetipName;

	private String targettypeName;

	private String stateName;

	private String servicetypeName;

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTargetipName() {
		return targetipName;
	}

	public void setTargetipName(String targetipName) {
		this.targetipName = targetipName;
	}

	public String getTargettypeName() {
		return targettypeName;
	}

	public void setTargettypeName(String targettypeName) {
		this.targettypeName = targettypeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getServicetypeName() {
		return servicetypeName;
	}

	public void setServicetypeName(String servicetypeName) {
		this.servicetypeName = servicetypeName;
	}

	/**
	 * 设置：测试记录ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：测试记录ID
	 */
	public Integer getId() {
		return id;
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
	 * 设置：任务类型
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	/**
	 * 获取：任务类型
	 */
	public Integer getTaskType() {
		return taskType;
	}
	/**
	 * 设置：测试目标类型
	 */
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	/**
	 * 获取：测试目标类型
	 */
	public Integer getTargetType() {
		return targetType;
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
	public void setTargetIp(Long targetIp) {
		this.targetIp = targetIp;
	}
	/**
	 * 获取：测试目标IP
	 */
	public Long getTargetIp() {
		return targetIp;
	}
	/**
	 * 设置：测试目标归属地
	 */
	public void setTargetLoc(String targetLoc) {
		this.targetLoc = targetLoc;
	}
	/**
	 * 获取：测试目标归属地
	 */
	public String getTargetLoc() {
		return targetLoc;
	}
	/**
	 * 设置：测试结果
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：测试结果
	 */
	public Integer getState() {
		return state;
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
	public void setDelay(BigDecimal delay) {
		this.delay = delay;
	}
	/**
	 * 获取：时延平均值
	 */
	public BigDecimal getDelay() {
		return delay;
	}
	/**
	 * 设置：往向时延
	 */
	public void setGDelay(BigDecimal gDelay) {
		this.gDelay = gDelay;
	}
	/**
	 * 获取：往向时延
	 */
	public BigDecimal getGDelay() {
		return gDelay;
	}
	/**
	 * 设置：返向时延
	 */
	public void setRDelay(BigDecimal rDelay) {
		this.rDelay = rDelay;
	}
	/**
	 * 获取：返向时延
	 */
	public BigDecimal getRDelay() {
		return rDelay;
	}
	/**
	 * 设置：时延标准差
	 */
	public void setDelayStd(BigDecimal delayStd) {
		this.delayStd = delayStd;
	}
	/**
	 * 获取：时延标准差
	 */
	public BigDecimal getDelayStd() {
		return delayStd;
	}
	/**
	 * 设置：往向时延标准差
	 */
	public void setGDelayStd(BigDecimal gDelayStd) {
		this.gDelayStd = gDelayStd;
	}
	/**
	 * 获取：往向时延标准差
	 */
	public BigDecimal getGDelayStd() {
		return gDelayStd;
	}
	/**
	 * 设置：返向时延标准差
	 */
	public void setRDelayStd(BigDecimal rDelayStd) {
		this.rDelayStd = rDelayStd;
	}
	/**
	 * 获取：返向时延标准差
	 */
	public BigDecimal getRDelayStd() {
		return rDelayStd;
	}
	/**
	 * 设置：时延方差
	 */
	public void setDelayVar(BigDecimal delayVar) {
		this.delayVar = delayVar;
	}
	/**
	 * 获取：时延方差
	 */
	public BigDecimal getDelayVar() {
		return delayVar;
	}
	/**
	 * 设置：往向时延方差
	 */
	public void setGDelayVar(BigDecimal gDelayVar) {
		this.gDelayVar = gDelayVar;
	}
	/**
	 * 获取：往向时延方差
	 */
	public BigDecimal getGDelayVar() {
		return gDelayVar;
	}
	/**
	 * 设置：返向时延方差
	 */
	public void setRDelayVar(BigDecimal rDelayVar) {
		this.rDelayVar = rDelayVar;
	}
	/**
	 * 获取：返向时延方差
	 */
	public BigDecimal getRDelayVar() {
		return rDelayVar;
	}
	/**
	 * 设置：抖动平均值
	 */
	public void setJitter(BigDecimal jitter) {
		this.jitter = jitter;
	}
	/**
	 * 获取：抖动平均值
	 */
	public BigDecimal getJitter() {
		return jitter;
	}
	/**
	 * 设置：往向抖动
	 */
	public void setGJitter(BigDecimal gJitter) {
		this.gJitter = gJitter;
	}
	/**
	 * 获取：往向抖动
	 */
	public BigDecimal getGJitter() {
		return gJitter;
	}
	/**
	 * 设置：返向抖动
	 */
	public void setRJitter(BigDecimal rJitter) {
		this.rJitter = rJitter;
	}
	/**
	 * 获取：返向抖动
	 */
	public BigDecimal getRJitter() {
		return rJitter;
	}
	/**
	 * 设置：抖动标准差
	 */
	public void setJitterStd(BigDecimal jitterStd) {
		this.jitterStd = jitterStd;
	}
	/**
	 * 获取：抖动标准差
	 */
	public BigDecimal getJitterStd() {
		return jitterStd;
	}
	/**
	 * 设置：往向抖动标准差
	 */
	public void setGJitterStd(BigDecimal gJitterStd) {
		this.gJitterStd = gJitterStd;
	}
	/**
	 * 获取：往向抖动标准差
	 */
	public BigDecimal getGJitterStd() {
		return gJitterStd;
	}
	/**
	 * 设置：返向抖动标准差
	 */
	public void setRJitterStd(BigDecimal rJitterStd) {
		this.rJitterStd = rJitterStd;
	}
	/**
	 * 获取：返向抖动标准差
	 */
	public BigDecimal getRJitterStd() {
		return rJitterStd;
	}
	/**
	 * 设置：抖动方差
	 */
	public void setJitterVar(BigDecimal jitterVar) {
		this.jitterVar = jitterVar;
	}
	/**
	 * 获取：抖动方差
	 */
	public BigDecimal getJitterVar() {
		return jitterVar;
	}
	/**
	 * 设置：往向抖动方差
	 */
	public void setGJitterVar(BigDecimal gJitterVar) {
		this.gJitterVar = gJitterVar;
	}
	/**
	 * 获取：往向抖动方差
	 */
	public BigDecimal getGJitterVar() {
		return gJitterVar;
	}
	/**
	 * 设置：返向抖动方差
	 */
	public void setRJitterVar(BigDecimal rJitterVar) {
		this.rJitterVar = rJitterVar;
	}
	/**
	 * 获取：返向抖动方差
	 */
	public BigDecimal getRJitterVar() {
		return rJitterVar;
	}
	/**
	 * 设置：丢包率
	 */
	public void setLossRate(BigDecimal lossRate) {
		this.lossRate = lossRate;
	}
	/**
	 * 获取：丢包率
	 */
	public BigDecimal getLossRate() {
		return lossRate;
	}
	/**
	 * 设置：往向丢包率
	 */
	public void setGLossRate(BigDecimal gLossRate) {
		this.gLossRate = gLossRate;
	}
	/**
	 * 获取：往向丢包率
	 */
	public BigDecimal getGLossRate() {
		return gLossRate;
	}
	/**
	 * 设置：返向丢包率
	 */
	public void setRLossRate(BigDecimal rLossRate) {
		this.rLossRate = rLossRate;
	}
	/**
	 * 获取：返向丢包率
	 */
	public BigDecimal getRLossRate() {
		return rLossRate;
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
}
