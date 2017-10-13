package io.cem.modules.cem.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public class TaskTargetRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//任务目标关系ID
	private Integer id;
	//任务ID
	private Integer taskId;
	//测试目标类型
	private Integer targetType;
	//测试目标ID
	private Integer targetId;
	//测试目标端口
	private String port;
	//备注
	private String remark;

	/**
	 * 设置：任务目标关系ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：任务目标关系ID
	 */
	public Integer getId() {
		return id;
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
	 * 设置：测试目标端口
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * 获取：测试目标端口
	 */
	public String getPort() {
		return port;
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
