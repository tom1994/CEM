package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-01-30 12:10:02
 */
public class RecordDayWebVideoEntity implements Serializable {
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
	//连接WEB服务器时延
	private Double wsConnDelay;
	//WEB页面时延
	private Double webPageDelay;
	//连接调度服务器时延
	private Double ssConnDelay;
	//获取视频地址时延
	private Double addressDelay;
	//连接媒体服务器时延
	private Double msConnDelay;
	//首帧时延
	private Double headFrameDelay;
	//首次缓冲时延
	private Double initBufferDelay;
	//视频加载时延
	private Double loadDelay;
	//总体缓冲时间
	private Double totalBufferDelay;
	//下载速率
	private Double downloadRate;
	//缓冲次数
	private Integer bufferTime;
	//备注
	private String remark;

	private Integer fail;

	private Integer total;

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
	 * 设置：连接WEB服务器时延
	 */
	public void setWsConnDelay(Double wsConnDelay) {
		this.wsConnDelay = wsConnDelay;
	}
	/**
	 * 获取：连接WEB服务器时延
	 */
	public Double getWsConnDelay() {
		return wsConnDelay;
	}
	/**
	 * 设置：WEB页面时延
	 */
	public void setWebPageDelay(Double webPageDelay) {
		this.webPageDelay = webPageDelay;
	}
	/**
	 * 获取：WEB页面时延
	 */
	public Double getWebPageDelay() {
		return webPageDelay;
	}
	/**
	 * 设置：连接调度服务器时延
	 */
	public void setSsConnDelay(Double ssConnDelay) {
		this.ssConnDelay = ssConnDelay;
	}
	/**
	 * 获取：连接调度服务器时延
	 */
	public Double getSsConnDelay() {
		return ssConnDelay;
	}
	/**
	 * 设置：获取视频地址时延
	 */
	public void setAddressDelay(Double addressDelay) {
		this.addressDelay = addressDelay;
	}
	/**
	 * 获取：获取视频地址时延
	 */
	public Double getAddressDelay() {
		return addressDelay;
	}
	/**
	 * 设置：连接媒体服务器时延
	 */
	public void setMsConnDelay(Double msConnDelay) {
		this.msConnDelay = msConnDelay;
	}
	/**
	 * 获取：连接媒体服务器时延
	 */
	public Double getMsConnDelay() {
		return msConnDelay;
	}
	/**
	 * 设置：首帧时延
	 */
	public void setHeadFrameDelay(Double headFrameDelay) {
		this.headFrameDelay = headFrameDelay;
	}
	/**
	 * 获取：首帧时延
	 */
	public Double getHeadFrameDelay() {
		return headFrameDelay;
	}
	/**
	 * 设置：首次缓冲时延
	 */
	public void setInitBufferDelay(Double initBufferDelay) {
		this.initBufferDelay = initBufferDelay;
	}
	/**
	 * 获取：首次缓冲时延
	 */
	public Double getInitBufferDelay() {
		return initBufferDelay;
	}
	/**
	 * 设置：视频加载时延
	 */
	public void setLoadDelay(Double loadDelay) {
		this.loadDelay = loadDelay;
	}
	/**
	 * 获取：视频加载时延
	 */
	public Double getLoadDelay() {
		return loadDelay;
	}
	/**
	 * 设置：总体缓冲时间
	 */
	public void setTotalBufferDelay(Double totalBufferDelay) {
		this.totalBufferDelay = totalBufferDelay;
	}
	/**
	 * 获取：总体缓冲时间
	 */
	public Double getTotalBufferDelay() {
		return totalBufferDelay;
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
	 * 设置：缓冲次数
	 */
	public void setBufferTime(Integer bufferTime) {
		this.bufferTime = bufferTime;
	}
	/**
	 * 获取：缓冲次数
	 */
	public Integer getBufferTime() {
		return bufferTime;
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
