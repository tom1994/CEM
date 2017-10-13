package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
public class RecordWebVideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//测试记录ID
	private Integer id;
	//探针ID
	private Integer probeId;
	//探针端口
	private String port;
	//任务ID
	private Integer taskId;
	//子业务类型
	private Integer serviceType;
	//任务类型
	private Integer taskType;
	//测试目标类型
	private Integer targetType;
	//测试目标ID
	private Integer targetId;
	//测试目标IP
	private Integer targetIp;
	//测试目标归属地
	private String targetLoc;
	//测试结果
	private Integer state;
	//记录日期
	private Date recordDate;
	//记录时间
	private String recordTime;
	//DNS时延
	private BigDecimal dnsDelay;
	//连接WEB服务器时延
	private BigDecimal wsConnDelay;
	//WEB页面时延
	private BigDecimal webPageDelay;
	//连接调度服务器时延
	private BigDecimal ssConnDelay;
	//获取视频地址时延
	private BigDecimal addressDelay;
	//连接媒体服务器时延
	private BigDecimal msConnDelay;
	//首帧时延
	private BigDecimal headFrameDelay;
	//首次缓冲时延
	private BigDecimal initBufferDelay;
	//视频加载时延
	private BigDecimal loadDelay;
	//总体缓冲时间
	private BigDecimal totalBufferDelay;
	//下载速率
	private BigDecimal downloadRate;
	//缓冲次数
	private Integer bufferTime;
	//备注
	private String remark;

	/**
	 * 设置：测试记录ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：测试记录ID
	 */
	public Integer getId() {
		return id;
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
	 * 设置：任务类型
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	/**
	 * 获取：任务类型
	 */
	public Integer getTaskType() {
		return taskType;
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
	 * 设置：测试目标IP
	 */
	public void setTargetIp(Integer targetIp) {
		this.targetIp = targetIp;
	}
	/**
	 * 获取：测试目标IP
	 */
	public Integer getTargetIp() {
		return targetIp;
	}
	/**
	 * 设置：测试目标归属地
	 */
	public void setTargetLoc(String targetLoc) {
		this.targetLoc = targetLoc;
	}
	/**
	 * 获取：测试目标归属地
	 */
	public String getTargetLoc() {
		return targetLoc;
	}
	/**
	 * 设置：测试结果
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：测试结果
	 */
	public Integer getState() {
		return state;
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
	public void setDnsDelay(BigDecimal dnsDelay) {
		this.dnsDelay = dnsDelay;
	}
	/**
	 * 获取：DNS时延
	 */
	public BigDecimal getDnsDelay() {
		return dnsDelay;
	}
	/**
	 * 设置：连接WEB服务器时延
	 */
	public void setWsConnDelay(BigDecimal wsConnDelay) {
		this.wsConnDelay = wsConnDelay;
	}
	/**
	 * 获取：连接WEB服务器时延
	 */
	public BigDecimal getWsConnDelay() {
		return wsConnDelay;
	}
	/**
	 * 设置：WEB页面时延
	 */
	public void setWebPageDelay(BigDecimal webPageDelay) {
		this.webPageDelay = webPageDelay;
	}
	/**
	 * 获取：WEB页面时延
	 */
	public BigDecimal getWebPageDelay() {
		return webPageDelay;
	}
	/**
	 * 设置：连接调度服务器时延
	 */
	public void setSsConnDelay(BigDecimal ssConnDelay) {
		this.ssConnDelay = ssConnDelay;
	}
	/**
	 * 获取：连接调度服务器时延
	 */
	public BigDecimal getSsConnDelay() {
		return ssConnDelay;
	}
	/**
	 * 设置：获取视频地址时延
	 */
	public void setAddressDelay(BigDecimal addressDelay) {
		this.addressDelay = addressDelay;
	}
	/**
	 * 获取：获取视频地址时延
	 */
	public BigDecimal getAddressDelay() {
		return addressDelay;
	}
	/**
	 * 设置：连接媒体服务器时延
	 */
	public void setMsConnDelay(BigDecimal msConnDelay) {
		this.msConnDelay = msConnDelay;
	}
	/**
	 * 获取：连接媒体服务器时延
	 */
	public BigDecimal getMsConnDelay() {
		return msConnDelay;
	}
	/**
	 * 设置：首帧时延
	 */
	public void setHeadFrameDelay(BigDecimal headFrameDelay) {
		this.headFrameDelay = headFrameDelay;
	}
	/**
	 * 获取：首帧时延
	 */
	public BigDecimal getHeadFrameDelay() {
		return headFrameDelay;
	}
	/**
	 * 设置：首次缓冲时延
	 */
	public void setInitBufferDelay(BigDecimal initBufferDelay) {
		this.initBufferDelay = initBufferDelay;
	}
	/**
	 * 获取：首次缓冲时延
	 */
	public BigDecimal getInitBufferDelay() {
		return initBufferDelay;
	}
	/**
	 * 设置：视频加载时延
	 */
	public void setLoadDelay(BigDecimal loadDelay) {
		this.loadDelay = loadDelay;
	}
	/**
	 * 获取：视频加载时延
	 */
	public BigDecimal getLoadDelay() {
		return loadDelay;
	}
	/**
	 * 设置：总体缓冲时间
	 */
	public void setTotalBufferDelay(BigDecimal totalBufferDelay) {
		this.totalBufferDelay = totalBufferDelay;
	}
	/**
	 * 获取：总体缓冲时间
	 */
	public BigDecimal getTotalBufferDelay() {
		return totalBufferDelay;
	}
	/**
	 * 设置：下载速率
	 */
	public void setDownloadRate(BigDecimal downloadRate) {
		this.downloadRate = downloadRate;
	}
	/**
	 * 获取：下载速率
	 */
	public BigDecimal getDownloadRate() {
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
