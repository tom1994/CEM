package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 报表策略
 */
public class ReportPolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String reportName;
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
	private Date startTime;
	//
	private Date endTime;
	//
	private String remark;
	//建立时间
	private Date createTime;

	private String cityName;

	private String countyName;

	private String probeName;


	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

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
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：
	 */
	public Date getEndTime() {
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
}
