package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//任务ID
	private Integer id;
	//名称
	private String name;
	//子业务类型
	private Integer serviceType;
	//任务类型
	private Integer type;
	//测试次数
	private Integer testNumber;
	//测试间隔
	private Integer testInterval;
	//调度策略ID
	private Integer schPolicyId;
	//参数
	private String parameter;
	//告警模板ID
	private Integer alarmTemplateId;
	//备注
	private String remark;
	//创建时间
	private Date createTime;

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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
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
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：任务类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：测试次数
	 */
	public void setTestNumber(Integer testNumber) {
		this.testNumber = testNumber;
	}
	/**
	 * 获取：测试次数
	 */
	public Integer getTestNumber() {
		return testNumber;
	}
	/**
	 * 设置：测试间隔
	 */
	public void setTestInterval(Integer testInterval) {
		this.testInterval = testInterval;
	}
	/**
	 * 获取：测试间隔
	 */
	public Integer getTestInterval() {
		return testInterval;
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
}
