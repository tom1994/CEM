package io.cem.modules.cem.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.utils.DateUtils;
import io.cem.common.utils.excel.annotation.ExcelIgnore;
import io.cem.common.utils.excel.annotation.ExportName;

import java.io.Serializable;
import java.util.*;


/**
 * @author Miao
 */
public class ProbeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//探针ID
	@ExcelIgnore
	private Integer id;
	//探针序列号
	@ExportName(exportName = "序列号")
	private String serialNumber;
	//探针名称
	@ExportName(exportName = "探针名")
	private String name;
	//所属探针组ID
	@ExcelIgnore
	private Integer groupId;

	//地市名称
	@ExportName(exportName = "地市")
	private String cityName;
	//区县名称
	@ExportName(exportName = "区县")
	private String areaName;

	@ExportName(exportName = "探针组")
	private String groupName;
	//探针类型
	@ExcelIgnore
	private Integer type;
	//接入层级
	@ExcelIgnore
	private Integer accessLayer;
	//IP类型
	@ExcelIgnore
	private Integer ipType;
	@ExcelIgnore
	private String iptypeName;

	//端口及IP
	@ExportName(exportName = "端口及ip")
	private String portIp;
	//归属地市
	@ExcelIgnore
	private Integer city;
	//归属区县
	@ExcelIgnore
	private Integer county;
	//详细归属地
	@ExportName(exportName = "详细地址")
	private String location;
	//上级探针
	@ExcelIgnore
	private Integer upstream;
	//接入BRAS名称
	@ExportName(exportName = "接入BRAS名称")
	private String brasName;
	//接入BRAS IP
	@ExportName(exportName = "接入BRASip")
	private String brasIp;
	//接入BRAS端口
	@ExportName(exportName = "接入BRAS端口")
	private String brasPort;
	//接入运营商
	@ExcelIgnore
	private Integer isp;

	//探针类型
	@ExportName(exportName = "探针类型")
	private String typeName;
	@ExportName(exportName = "接入运营商")
	private String ispName;
	//探针状态
	@ExcelIgnore
	private Integer status;
	//探针状态
	@ExportName(exportName = "探针状态")
	private String statusName;
	//设备型号
	@ExportName(exportName = "设备型号")
	private String device;
	//软件版本
	@ExportName(exportName = "软件版本")
	private String version;
	//注册时间
	@ExportName(exportName = "注册时间")
	private Date registerTime;
	//并发任务数
	@ExcelIgnore
	private Integer concurrentTask;
	//心跳间隔
	@ExportName(exportName = "心跳间隔")
	private Integer hbInterval;
	//上次心跳时间
	@ExportName(exportName = "上次心跳时间")
	private Date lastHbTime;
	//任务间隔
	@ExcelIgnore
	private Integer taskInterval;
	//上报间隔
	@ExcelIgnore
	private Integer reportInterval;
	//上次上报时间
	@ExportName(exportName = "上次上报时间")
	private Date lastReportTime;
	//更新间隔
	@ExcelIgnore
	private Integer updateInterval;
	//上次更新时间
	@ExportName(exportName = "上次更新时间")
	private Date lastUpdateTime;
	//备注
	@ExportName(exportName = "备注")
	private String remark;
	//创建时间
	@ExportName(exportName = "创建时间")
	private Date createTime;


	//接入层级
	@ExportName(exportName = "接入层级")
	private String layerName;
	@ExportName(exportName = "上层探针名称")
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

	public String getGroupName() { return groupName; }

	public void setGroupName(String groupName) { this.groupName = groupName; }
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
	public String getIptypeName() { return iptypeName; }
	public void setIptypeName(String iptypeName) { this.iptypeName = iptypeName; }

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

	public String getIspName() { return ispName; }

	public void setIspName(String ispName) { this.ispName = ispName; }
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
	public String getRegisterTime() {
		return DateUtils.format(registerTime,"yyyy-MM-dd HH:mm:ss");
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
	public String getLastHbTime() {
		return DateUtils.format(lastHbTime,"yyyy-MM-dd HH:mm:ss");
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
	public String getLastReportTime() {
		return DateUtils.format(lastReportTime,"yyyy-MM-dd HH:mm:ss");
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
	public String getLastUpdateTime() {
		return DateUtils.format(lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
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
	public String getCreateTime() {
		return DateUtils.format(createTime,"yyyy-MM-dd HH:mm:ss");
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

	public String getPortIpString(){
		JSONArray jsonArray = JSON.parseArray(portIp);
		StringBuilder newPortIp = new StringBuilder();
		Map map = new HashMap();
		map.put(1,"静态ip");
		map.put(2,"DHCP动态分配");
		map.put(3,"PPPoE拨号");
		for (int i = 0; i <jsonArray.size(); i++) {
			newPortIp.append("[").append("端口:").append(jsonArray.getJSONObject(i).get("port")).append(",");
			newPortIp.append("IP:").append(jsonArray.getJSONObject(i).get("ip")).append(",");
			newPortIp.append("类型:").append(map.get(jsonArray.getJSONObject(i).get("ip_type"))).append("]").append(" ");
		}
		return newPortIp.toString();
	}

/*	public String getLastReportTimeDate() {
		return DateUtils.format(lastReportTime);
	}

	public String getLastHbTimeDate() {
		return DateUtils.format(lastHbTime);
	}

	public String getCreateTimeDate() {
		return DateUtils.format(createTime);
	}*/
}
