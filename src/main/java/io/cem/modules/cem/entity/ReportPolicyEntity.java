package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;



/**
 * 报表策略
 */
public class ReportPolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer probeId;
	//子业务类型
	private Integer serviceType;
	//查询方式
	private Integer queryType;
	//查询粒度
	private Integer interval;
	//
	private Time startTime;
	//
	private Time endTime;
	//
	private String remark;
	//建立时间
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
	 * 设置：
	 */
	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}
	/**
	 * 获取：
	 */
	public Integer getProbeId() {
		return probeId;
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
	 * 设置：查询方式
	 */
	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}
	/**
	 * 获取：查询方式
	 */
	public Integer getQueryType() {
		return queryType;
	}
	/**
	 * 设置：查询粒度
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	/**
	 * 获取：查询粒度
	 */
	public Integer getInterval() {
		return interval;
	}
	/**
	 * 设置：
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public Time getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：
	 */
	public Time getEndTime() {
		return endTime;
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
	/**
	 * 设置：建立时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：建立时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
