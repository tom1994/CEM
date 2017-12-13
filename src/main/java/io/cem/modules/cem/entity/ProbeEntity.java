package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * @author Miao
 */
public class ProbeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//探针ID
	private Integer id;
	//探针序列号
	private String serialNumber;
	//探针名称
	private String name;
	//所属探针组ID
	private Integer groupId;
	//探针类型
	private Integer type;
	//接入层级
	private Integer accessLayer;
	//IP类型
	private Integer ipType;
	//端口及IP
	private String portIp;
	//归属地市
	private Integer city;
	//归属区县
	private Integer county;
	//详细归属地
	private String location;
	//上级探针
	private Integer upstream;
	//接入BRAS名称
	private String brasName;
	//接入BRAS IP
	private String brasIp;
	//接入BRAS端口
	private String brasPort;
	//接入运营商
	private Integer isp;
	//探针状态
	private Integer status;
	//设备型号
	private String device;
	//软件版本
	private String version;
	//注册时间
	private Date registerTime;
	//并发任务数
	private Integer concurrentTask;
	//心跳间隔
	private Integer hbInterval;
	//上次心跳时间
	private Date lastHbTime;
	//任务间隔
	private Integer taskInterval;
	//上报间隔
	private Integer reportInterval;
	//上次上报时间
	private Date lastReportTime;
	//更新间隔
	private Integer updateInterval;
	//上次更新时间
	private Date lastUpdateTime;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	//地市名称
	private String cityName;
	//区县名称
	private String areaName;
	//探针类型
	private String typeName;
	//探针状态
	private String statusName;
	//接入层级
	private String layerName;

	private String upstreamName;
	/**
	 * 设置：探针ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：探针ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：探针序列号
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 获取：探针序列号
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * 设置：探针名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：探针名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：所属探针组ID
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：所属探针组ID
	 */
	public Integer getGroupId() {
		return groupId;
	}
	/**
	 * 设置：探针类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：探针类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：接入层级
	 */
	public void setAccessLayer(Integer accessLayer) {
		this.accessLayer = accessLayer;
	}
	/**
	 * 获取：接入层级
	 */
	public Integer getAccessLayer() {
		return accessLayer;
	}
	/**
	 * 设置：IP类型
	 */
	public void setIpType(Integer ipType) {
		this.ipType = ipType;
	}
	/**
	 * 获取：IP类型
	 */
	public Integer getIpType() {
		return ipType;
	}
	/**
	 * 设置：端口及IP
	 */
	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}
	/**
	 * 获取：端口及IP
	 */
	public String getPortIp() {
		return portIp;
	}
	/**
	 * 设置：归属地市
	 */
	public void setCity(Integer city) {
		this.city = city;
	}
	/**
	 * 获取：归属地市
	 */
	public Integer getCity() {
		return city;
	}
	/**
	 * 设置：归属区县
	 */
	public void setCounty(Integer county) {
		this.county = county;
	}
	/**
	 * 获取：归属区县
	 */
	public Integer getCounty() {
		return county;
	}
	/**
	 * 设置：详细归属地
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * 获取：详细归属地
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * 设置：上级探针
	 */
	public void setUpstream(Integer upstream) {
		this.upstream = upstream;
	}
	/**
	 * 获取：上级探针
	 */
	public Integer getUpstream() {
		return upstream;
	}
	/**
	 * 设置：接入BRAS名称
	 */
	public void setBrasName(String brasName) {
		this.brasName = brasName;
	}
	/**
	 * 获取：接入BRAS名称
	 */
	public String getBrasName() {
		return brasName;
	}
	/**
	 * 设置：接入BRAS IP
	 */
	public void setBrasIp(String brasIp) {
		this.brasIp = brasIp;
	}
	/**
	 * 获取：接入BRAS IP
	 */
	public String getBrasIp() {
		return brasIp;
	}
	/**
	 * 设置：接入BRAS端口
	 */
	public void setBrasPort(String brasPort) {
		this.brasPort = brasPort;
	}
	/**
	 * 获取：接入BRAS端口
	 */
	public String getBrasPort() {
		return brasPort;
	}
	/**
	 * 设置：接入运营商
	 */
	public void setIsp(Integer isp) {
		this.isp = isp;
	}
	/**
	 * 获取：接入运营商
	 */
	public Integer getIsp() {
		return isp;
	}
	/**
	 * 设置：探针状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：探针状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：设备型号
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * 获取：设备型号
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * 设置：软件版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：软件版本
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：注册时间
	 */
	public Date getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：并发任务数
	 */
	public void setConcurrentTask(Integer concurrentTask) {
		this.concurrentTask = concurrentTask;
	}
	/**
	 * 获取：并发任务数
	 */
	public Integer getConcurrentTask() {
		return concurrentTask;
	}
	/**
	 * 设置：心跳间隔
	 */
	public void setHbInterval(Integer hbInterval) {
		this.hbInterval = hbInterval;
	}
	/**
	 * 获取：心跳间隔
	 */
	public Integer getHbInterval() {
		return hbInterval;
	}
	/**
	 * 设置：上次心跳时间
	 */
	public void setLastHbTime(Date lastHbTime) {
		this.lastHbTime = lastHbTime;
	}
	/**
	 * 获取：上次心跳时间
	 */
	public Date getLastHbTime() {
		return lastHbTime;
	}
	/**
	 * 设置：任务间隔
	 */
	public void setTaskInterval(Integer taskInterval) {
		this.taskInterval = taskInterval;
	}
	/**
	 * 获取：任务间隔
	 */
	public Integer getTaskInterval() {
		return taskInterval;
	}
	/**
	 * 设置：上报间隔
	 */
	public void setReportInterval(Integer reportInterval) {
		this.reportInterval = reportInterval;
	}
	/**
	 * 获取：上报间隔
	 */
	public Integer getReportInterval() {
		return reportInterval;
	}
	/**
	 * 设置：上次上报时间
	 */
	public void setLastReportTime(Date lastReportTime) {
		this.lastReportTime = lastReportTime;
	}
	/**
	 * 获取：上次上报时间
	 */
	public Date getLastReportTime() {
		return lastReportTime;
	}
	/**
	 * 设置：更新间隔
	 */
	public void setUpdateInterval(Integer updateInterval) {
		this.updateInterval = updateInterval;
	}
	/**
	 * 获取：更新间隔
	 */
	public Integer getUpdateInterval() {
		return updateInterval;
	}
	/**
	 * 设置：上次更新时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 获取：上次更新时间
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
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
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTypeName() { return typeName; }

	public void setTypeName(String typeName) { this.typeName = typeName; }

	public String getStatusName() {	return statusName; }

	public void setStatusName(String statusName) { this.statusName = statusName; }

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getUpstreamName() {
		return upstreamName;
	}

	public void setUpstreamName(String upstreamName) {
		this.upstreamName = upstreamName;
	}
}
