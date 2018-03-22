package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 */
public class RecordHourWebPageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//记录ID
	private Integer id;
	//地市
	private Integer cityId;
	//区县
	private Integer countyId;
	//探针ID
	private Integer probeId;
	//探针端口
	private String port;
	//任务ID
	private Integer taskId;
	//子业务类型
	private Integer serviceType;
	//测试目标ID
	private Integer targetId;
	//测试目标IP
	private Long targetIp;
	//记录日期
	private Date recordDate;
	//记录时间
	private String recordTime;
	//DNS时延
	private Double dnsDelay;
	//连接时延
	private Double connDelay;
	//首字节时延
	private Double headbyteDelay;
	//页面文件时延
	private Double pageFileDelay;
	//重定向时延
	private Double redirectDelay;
	//首屏时延
	private Double aboveFoldDelay;
	//页面元素时延
	private Double pageElementDelay;
	//下载速率
	private Double downloadRate;
	//备注
	private String remark;
	//地市名称
	private String cityName;
	//区县名称
	private String areaName;
	//探针名称
	private String probeName;
	//测试目标名称
	private String targetName;

	private String taskName;

	private String targetipName;

	private String targettypeName;

	private String stateName;

	private String servicetypeName;

	private Integer fail;

	private Integer total;

	private Integer accessLayer;

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTargetipName() {
		return targetipName;
	}

	public void setTargetipName(String targetipName) {
		this.targetipName = targetipName;
	}

	public String getTargettypeName() {
		return targettypeName;
	}

	public void setTargettypeName(String targettypeName) {
		this.targettypeName = targettypeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getServicetypeName() {
		return servicetypeName;
	}

	public void setServicetypeName(String servicetypeName) {
		this.servicetypeName = servicetypeName;
	}

	/**
	 * 设置：记录ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：记录ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：地市
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：地市
	 */
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * 设置：区县
	 */
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	/**
	 * 获取：区县
	 */
	public Integer getCountyId() {
		return countyId;
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
	 * 设置：测试目标IP
	 */
	public void setTargetIp(Long targetIp) {
		this.targetIp = targetIp;
	}
	/**
	 * 获取：测试目标IP
	 */
	public Long getTargetIp() {
		return targetIp;
	}
	/**
	 * 设置：记录日期
	 */
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	/**
	 * 获取：记录日期
	 */
	public Date getRecordDate() {
		return recordDate;
	}
	/**
	 * 设置：记录时间
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	/**
	 * 获取：记录时间
	 */
	public String getRecordTime() {
		return recordTime;
	}
	/**
	 * 设置：DNS时延
	 */
	public void setDnsDelay(Double dnsDelay) {
		this.dnsDelay = dnsDelay;
	}
	/**
	 * 获取：DNS时延
	 */
	public Double getDnsDelay() {
		return dnsDelay;
	}
	/**
	 * 设置：连接时延
	 */
	public void setConnDelay(Double connDelay) {
		this.connDelay = connDelay;
	}
	/**
	 * 获取：连接时延
	 */
	public Double getConnDelay() {
		return connDelay;
	}
	/**
	 * 设置：首字节时延
	 */
	public void setHeadbyteDelay(Double headbyteDelay) {
		this.headbyteDelay = headbyteDelay;
	}
	/**
	 * 获取：首字节时延
	 */
	public Double getHeadbyteDelay() {
		return headbyteDelay;
	}
	/**
	 * 设置：页面文件时延
	 */
	public void setPageFileDelay(Double pageFileDelay) {
		this.pageFileDelay = pageFileDelay;
	}
	/**
	 * 获取：页面文件时延
	 */
	public Double getPageFileDelay() {
		return pageFileDelay;
	}
	/**
	 * 设置：重定向时延
	 */
	public void setRedirectDelay(Double redirectDelay) {
		this.redirectDelay = redirectDelay;
	}
	/**
	 * 获取：重定向时延
	 */
	public Double getRedirectDelay() {
		return redirectDelay;
	}
	/**
	 * 设置：首屏时延
	 */
	public void setAboveFoldDelay(Double aboveFoldDelay) {
		this.aboveFoldDelay = aboveFoldDelay;
	}
	/**
	 * 获取：首屏时延
	 */
	public Double getAboveFoldDelay() {
		return aboveFoldDelay;
	}
	/**
	 * 设置：页面元素时延
	 */
	public void setPageElementDelay(Double pageElementDelay) {
		this.pageElementDelay = pageElementDelay;
	}
	/**
	 * 获取：页面元素时延
	 */
	public Double getPageElementDelay() {
		return pageElementDelay;
	}
	/**
	 * 设置：下载速率
	 */
	public void setDownloadRate(Double downloadRate) {
		this.downloadRate = downloadRate;
	}
	/**
	 * 获取：下载速率
	 */
	public Double getDownloadRate() {
		return downloadRate;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Integer getAccessLayer() {
		return accessLayer;
	}

	public void setAccessLayer(Integer accessLayer) {
		this.accessLayer = accessLayer;
	}
}
