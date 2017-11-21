package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public class TargetEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//测试目标ID
	private Integer id;
	//名称
	private String targetName;
	//测试目标取值
	private String value;
	//子业务类型
	private Integer superserviceType;
	//所属测试目标组
	private Integer groupId;
	//备注
	private String remark;
	//创建时间
	private Date createTime;

	/**
	 * 设置：测试目标ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：测试目标ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	/**
	 * 获取：名称
	 */
	public String getTargetName() {
		return targetName;
	}
	/**
	 * 设置：测试目标取值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：测试目标取值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：业务类型
	 */
	public Integer getSuperserviceType() {
		return superserviceType;
	}

	public void setSuperserviceType(Integer superserviceType) {
		this.superserviceType = superserviceType;
	}

	/**
	 * 设置：所属测试目标组
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：所属测试目标组
	 */
	public Integer getGroupId() {
		return groupId;
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
