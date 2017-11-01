package io.cem.modules.cem.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public class TaskProbeRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//任务探针关系ID
	private Integer id;
	//任务ID
	private Integer taskId;
	//探针ID
	private Integer probeId;
	//探针端口
	private String port;
	//任务状态
	private Integer status;
	//备注
	private String remark;
	//任务名称
	private String taskName;
	//探针名称
	private String probeName;

	/**
	 * 设置：任务探针关系ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：任务探针关系ID
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
	 * 设置：任务状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：任务状态
	 */
	public Integer getStatus() {
		return status;
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
	 * 设置：探针名称
	 */
	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	/**
	 * 获取：探针名称
	 */
	public String getProbeName() {
		return probeName;
	}
	/**
	 * 设置：任务名称
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 获取：任务名称
	 */
	public String getTaskName() {
		return taskName;
	}
}
