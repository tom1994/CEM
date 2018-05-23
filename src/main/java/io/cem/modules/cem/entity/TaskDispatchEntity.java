package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 任务分配
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

	private int[] targetIds;

	private int[] targetGroupIds;
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

	private int[] probeIds;

	private int[] probeGroupIds;

	private Integer probeGroupId;

	private String targetName;

	private String taskName;

	private String layerName;

	private Integer serviceType;

	private String spName;

	private Date createTime;

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

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}


	public int[] getProbeIds() {
		return probeIds;
	}

	public void setProbeIds(int[] probeIds) {
		this.probeIds = probeIds;
	}

	public int[] getProbeGroupIds() {
		return probeGroupIds;
	}

	public void setProbeGroupIds(int[] probeGroupIds) {
		this.probeGroupIds = probeGroupIds;
	}

	public Integer getProbeGroupId() {
		return probeGroupId;
	}

	public void setProbeGroupId(Integer probeGroupId) {
		this.probeGroupId = probeGroupId;
	}

	public int[] getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(int[] targetIds) {
		this.targetIds = targetIds;
	}
	public int[] getTargetGroupIds() {
		return targetGroupIds;
	}

	public void setTargetGroupIds(int[] targetGroupIds) {
		this.targetGroupIds = targetGroupIds;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
