package io.cem.modules.cem.entity;

import io.cem.common.utils.excel.annotation.ExcelIgnore;
import io.cem.common.utils.excel.annotation.ExportName;

import java.io.Serializable;
import java.util.Date;



/**
 */
public class RecordRadiusEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//测试记录ID
	private Integer id;
	//探针ID
	@ExcelIgnore
	private Integer probeId;
	//探针端口
	@ExportName(exportName = "端口")
	private String port;
	//任务ID
	@ExcelIgnore
	private Integer taskId;
	//子业务类型
	@ExcelIgnore
	private Integer serviceType;
	//任务类型
	@ExcelIgnore
	private Integer taskType;
	//测试目标类型
	@ExcelIgnore
	private Integer targetType;
	//测试目标ID
	@ExcelIgnore
	private Integer targetId;
	//测试目标IP
	@ExcelIgnore
	private Long targetIp;
	//测试目标归属地
	@ExcelIgnore
	private String targetLoc;
	//测试结果
	@ExcelIgnore
	private Integer state;
	//记录日期
	@ExportName(exportName = "记录日期")
	private Date recordDate;
	//记录时间
	@ExportName(exportName = "记录时间")
	private String recordTime;
	//时延平均值
	@ExportName(exportName = "时延平均值")
	private Double delay;
	//认证成功率
	@ExportName(exportName = "认证成功率")
	private Double successRate;
	//备注
	@ExportName(exportName = "备注")
	private String remark;
	@ExportName(exportName = "探针")
	private String probeName;
	@ExportName(exportName = "测试目标")
	private String targetName;
	@ExportName(exportName = "任务名称")
	private String taskName;
	@ExportName(exportName = "任务ip")
	private String targetipName;
@ExcelIgnore
	private String targettypeName;
@ExcelIgnore
	private String stateName;
@ExcelIgnore
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
	 * 设置：认证成功率
	 */
	public void setSuccessRate(Double successRate) {
		this.successRate = successRate;
	}
	/**
	 * 获取：认证成功率
	 */
	public Double getSuccessRate() {
		return successRate;
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
