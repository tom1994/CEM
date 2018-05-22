package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 告警模版
 */
public class AlarmTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//告警模板ID
	private Integer id;
	//名称
	private String atName;
	//子业务类型
	private Integer serviceType;
	//阈值取值
	private String value;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	//子业务名称
	private String serviceName;

	/**
	 * 设置：告警模板ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：告警模板ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setAtName(String atName) {
		this.atName = atName;
	}
	/**
	 * 获取：名称
	 */
	public String getAtName() {
		return atName;
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
	 * 设置：阈值取值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：阈值取值
	 */
	public String getValue() {
		return value;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
