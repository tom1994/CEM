package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 任务模板表
 */
public class TaskTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String name;
	//
	private Integer serviceType;
	//
	private Integer schPolicyId;
	//
	private String templateParam;
	//
	private Integer alarmTemplateId;
	//
	private String remark;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * 获取：
	 */
	public Integer getServiceType() {
		return serviceType;
	}
	/**
	 * 设置：
	 */
	public void setSchPolicyId(Integer schPolicyId) {
		this.schPolicyId = schPolicyId;
	}
	/**
	 * 获取：
	 */
	public Integer getSchPolicyId() {
		return schPolicyId;
	}
	/**
	 * 设置：
	 */
	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}
	/**
	 * 获取：
	 */
	public String getTemplateParam() {
		return templateParam;
	}
	/**
	 * 设置：
	 */
	public void setAlarmTemplateId(Integer alarmTemplateId) {
		this.alarmTemplateId = alarmTemplateId;
	}
	/**
	 * 获取：
	 */
	public Integer getAlarmTemplateId() {
		return alarmTemplateId;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
}
