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
public class RecordDayGameEntity implements Serializable {
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
	private Integer targetIp;
	//记录日期
	private Date recordDate;
	//记录时间
	private String recordTime;
	//DNS时延
	private Double dnsDelay;
	//连接时延
	private Double connDelay;
	//游戏数据包时延
	private Double packetDelay;
	//游戏数据包抖动
	private Double packetJitter;
	//游戏数据包丢包率
	private Double lossRate;
	//备注
	private String remark;

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
	 * 设置：游戏数据包时延
	 */
	public void setPacketDelay(Double packetDelay) {
		this.packetDelay = packetDelay;
	}
	/**
	 * 获取：游戏数据包时延
	 */
	public Double getPacketDelay() {
		return packetDelay;
	}
	/**
	 * 设置：游戏数据包抖动
	 */
	public void setPacketJitter(Double packetJitter) {
		this.packetJitter = packetJitter;
	}
	/**
	 * 获取：游戏数据包抖动
	 */
	public Double getPacketJitter() {
		return packetJitter;
	}
	/**
	 * 设置：游戏数据包丢包率
	 */
	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}
	/**
	 * 获取：游戏数据包丢包率
	 */
	public Double getLossRate() {
		return lossRate;
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
