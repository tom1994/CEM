package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
public class AlarmRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//告警记录ID
	private Integer id;
	//告警类型
	private Integer type;
	//告警级别
	private Integer level;
	//告警状态
	private Integer status;
	//探针ID
	private Integer probeId;
	//任务ID
	private Integer taskId;
	//测试记录ID
	private Integer recordId;
	//子业务类型
	private Integer serviceType;
	//测试目标类别
	private Integer targetType;
	//测试目标ID
	private Integer targetId;
	//记录时间
	private Date recordTime;
	//备注
	private String remark;
	//探针名称
	private String probeName;

	/**
	 * 设置：告警记录ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：告警记录ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：告警类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：告警类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：告警级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：告警级别
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：告警状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：告警状态
	 */
	public Integer getStatus() {
		return status;
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
	 * 设置：测试记录ID
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：测试记录ID
	 */
	public Integer getRecordId() {
		return recordId;
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
	 * 设置：测试目标类别
	 */
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	/**
	 * 获取：测试目标类别
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
	 * 设置：记录时间
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	/**
	 * 获取：记录时间
	 */
	public Date getRecordTime() {
		return recordTime;
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

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
}
