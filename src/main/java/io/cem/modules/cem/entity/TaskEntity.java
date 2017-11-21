package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//任务ID
	private Integer id;
	//名称
	private String taskName;
	//子业务类型
	private Integer serviceType;
	//调度策略ID
	private Integer schPolicyId;
	//参数
	private String parameter;
	//告警模板ID
	private Integer alarmTemplateId;
	//标记删除
	private Integer isDeleted;
	//备注
	private String remark;
	//创建时间
	private Date createTime;

	private String atName;

	private String spName;

	private String countDispatch;

	/**
	 * 设置：任务ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：任务ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 获取：名称
	 */
	public String getTaskName() {
		return taskName;
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
	 * 设置：调度策略ID
	 */
	public void setSchPolicyId(Integer schPolicyId) {
		this.schPolicyId = schPolicyId;
	}
	/**
	 * 获取：调度策略ID
	 */
	public Integer getSchPolicyId() {
		return schPolicyId;
	}
	/**
	 * 设置：参数
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**
	 * 获取：参数
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * 设置：告警模板ID
	 */
	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}
	/**
	 * 获取：告警模板ID
	 */
	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}
	/**
	 * 设置：标记删除
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：标记删除
	 */
	public Integer getIsDeleted() {
		return isDeleted;
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
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getCountDispatch() {
		return countDispatch;
	}

	public void setCountDispatch(String countDispatch) {
		this.countDispatch = countDispatch;
	}
}
