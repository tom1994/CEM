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
public class TaskDispatchEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//任务ID
	private Integer taskId;
	//探针ID
	private Integer probeId;
	//探针端口
	private String probePort;
	//测试目标
	private String target;
	//测试目标组
	private String targetGroup;
	//是否按需任务
	private Integer isOndemand;
	//按需测试次数
	private Integer testNumber;
	//按需测试间隔
	private Integer testInterval;
	//任务状态
	private Integer status;
	//备注
	private String remark;

	private String probeName;

	private String location;

	private String accessLayer;

	private String probeIds;

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
	public void setProbePort(String probePort) {
		this.probePort = probePort;
	}
	/**
	 * 获取：探针端口
	 */
	public String getProbePort() {
		return probePort;
	}
	/**
	 * 设置：测试目标
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * 获取：测试目标
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * 设置：测试目标组
	 */
	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
	}
	/**
	 * 获取：测试目标组
	 */
	public String getTargetGroup() {
		return targetGroup;
	}
	/**
	 * 设置：是否按需任务
	 */
	public void setIsOndemand(Integer isOndemand) {
		this.isOndemand = isOndemand;
	}
	/**
	 * 获取：是否按需任务
	 */
	public Integer getIsOndemand() {
		return isOndemand;
	}
	/**
	 * 设置：按需测试次数
	 */
	public void setTestNumber(Integer testNumber) {
		this.testNumber = testNumber;
	}
	/**
	 * 获取：按需测试次数
	 */
	public Integer getTestNumber() {
		return testNumber;
	}
	/**
	 * 设置：按需测试间隔
	 */
	public void setTestInterval(Integer testInterval) {
		this.testInterval = testInterval;
	}
	/**
	 * 获取：按需测试间隔
	 */
	public Integer getTestInterval() {
		return testInterval;
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

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccessLayer() {
		return accessLayer;
	}

	public void setAccessLayer(String accessLayer) {
		this.accessLayer = accessLayer;
	}


	public String getProbeIds() {
		return probeIds;
	}

	public void setProbeIds(String probeIds) {
		this.probeIds = probeIds;
	}
}
