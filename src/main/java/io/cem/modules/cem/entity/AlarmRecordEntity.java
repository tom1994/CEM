package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**

 */
public class AlarmRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//告警记录ID
	private Integer id;
	//告警类型
	private Integer type;
	//告警级别
	private Integer level;
	//告警状态
	private Integer status;
	//探针ID
	private Integer probeId;
	//任务ID
	private Integer taskId;
	//测试记录ID
	private Integer recordId;
	//子业务类型
	private Integer serviceType;
	//测试目标类别
	private Integer targetType;
	//测试目标ID
	private Integer targetId;
	//记录时间
	private Date recordTime;
	//备注
	private String remark;
	//探针名称
	private String probeName;
	//子业务名称
	private String dataName;
	//目标地址
	private String targetName;

	/*ping details*/
	//时延平均值
	private Double pingDelay;
	//时延标准差
	private Double pingDelayStd;
	//时延方差
	private Double pingDelayVar;
	//抖动平均值
	private Double pingJitter;
	//抖动标准差
	private Double pingJitterStd;
	//抖动方差
	private Double pingJitterVar;
	//丢包率
	private Double pingLossRate;



	/*tracert route details*/
	//时延平均值
	private Double tracertDelay;
	//时延标准差
	private Double tracertDelayStd;
	//时延方差
	private Double tracertDelayVar;
	//抖动平均值
	private Double tracertJitter;
	//抖动标准差
	private Double tracertJitterStd;
	//抖动方差
	private Double tracertJitterVar;
	//丢包率
	private Double tracertLossRate;


	/*sla details*/
	//时延平均值
	private Double slaDelay;
	//往向时延
	private Double slaGDelay;
	//返向时延
	private Double slaRDelay;
	//抖动平均值
	private Double slaJitter;
	//往向抖动
	private Double slaGJitter;
	//返向抖动
	private Double slaRJitter;
	//丢包率
	private Double slaLossRate;


	/*dns details*/
	//时延平均值
	private Double dnsDelay;
	//查询成功率
	private Double dnsSuccessRate;

	/*dhcp details*/
	//时延平均值
	private Double dhcpDelay;
	//查询成功率
	private Double dhcpSuccessRate;

	/*pppoe details*/
	//时延平均值
	private Double pppoeDelay;
	//掉线率
	private Double pppoeDropRate;
	//查询成功率
	private Double pppoeSuccessRate;

	/*radius details*/
	//时延平均值
	private Double radiusDelay;
	//认证成功率
	private Double radiusSuccessRate;

	/*web page details*/
	//DNS时延
	private Double webpageDnsDelay;
	//连接时延
	private Double webpageConnDelay;
	//首字节时延
	private Double webpageHeadbyteDelay;
	//页面文件时延
	private Double webpagePageFileDelay;
	//重定向时延
	private Double webpageRedirectDelay;
	//首屏时延
	private Double webpageAboveFoldDelay;
	//页面元素时延
	private Double webpagePageElementDelay;
	//下载速率
	private Double webpageDownloadRate;

	/*web download details*/
	//DNS时延
	private Double webDownloadDnsDelay;
	//连接时延
	private Double webDownloadConnDelay;
	//首字节时延
	private Double webDownloadHeadbyteDelay;
	//下载速率
	private Double webDownloadDownloadRate;

	/*ftp details*/
	//DNS时延
	private Double ftpDnsDelay;
	//连接时延
	private Double ftpConnDelay;
	//登录时延
	private Double ftpLoginDelay;
	//首字节时延
	private Double ftpHeadbyteDelay;
	//下载速率
	private Double ftpDownloadRate;
	//上传速率
	private Double ftpUploadRate;



	/*web video details*/
	//DNS时延
	private Double webVideoDnsDelay;
	//连接WEB服务器时延
	private Double webVideoWsConnDelay;
	//WEB页面时延
	private Double webVideoWebPageDelay;
	//连接调度服务器时延
	private Double webVideoSsConnDelay;
	//获取视频地址时延
	private Double webVideoAddressDelay;
	//连接媒体服务器时延
	private Double webVideoMsConnDelay;
	//首帧时延
	private Double webVideoHeadFrameDelay;
	//首次缓冲时延
	private Double webVideoInitBufferDelay;
	//视频加载时延
	private Double webVideoLoadDelay;
	//总体缓冲时间
	private Double webVideoTotalBufferDelay;
	//下载速率
	private Double webVideoDownloadRate;
	//缓冲次数
	private Integer webVideoBufferTime;

	/*game details*/
	//DNS时延
	private Double gameDnsDelay;
	//连接时延
	private Double gameConnDelay;
	//游戏数据包时延
	private Double gamePacketDelay;
	//游戏数据包抖动
	private Double gamePacketJitter;
	//游戏数据包丢包率
	private Double gameLossRate;

	/**
	 * 设置：告警记录ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：告警记录ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：告警类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：告警类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：告警级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：告警级别
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：告警状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：告警状态
	 */
	public Integer getStatus() {
		return status;
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
	 * 设置：测试记录ID
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：测试记录ID
	 */
	public Integer getRecordId() {
		return recordId;
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
	 * 设置：测试目标类别
	 */
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	/**
	 * 获取：测试目标类别
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
	 * 设置：记录时间
	 */
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	/**
	 * 获取：记录时间
	 */
	public Date getRecordTime() {
		return recordTime;
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

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Double getPingDelay() {
		return pingDelay;
	}

	public void setPingDelay(Double pingDelay) {
		this.pingDelay = pingDelay;
	}

	public Double getPingDelayStd() {
		return pingDelayStd;
	}

	public void setPingDelayStd(Double pingDelayStd) {
		this.pingDelayStd = pingDelayStd;
	}

	public Double getPingDelayVar() {
		return pingDelayVar;
	}

	public void setPingDelayVar(Double pingDelayVar) {
		this.pingDelayVar = pingDelayVar;
	}

	public Double getPingJitter() {
		return pingJitter;
	}

	public void setPingJitter(Double pingJitter) {
		this.pingJitter = pingJitter;
	}

	public Double getPingJitterStd() {
		return pingJitterStd;
	}

	public void setPingJitterStd(Double pingJitterStd) {
		this.pingJitterStd = pingJitterStd;
	}

	public Double getPingJitterVar() {
		return pingJitterVar;
	}

	public void setPingJitterVar(Double pingJitterVar) {
		this.pingJitterVar = pingJitterVar;
	}

	public Double getPingLossRate() {
		return pingLossRate;
	}

	public void setPingLossRate(Double pingLossRate) {
		this.pingLossRate = pingLossRate;
	}

	public Double getTracertDelay() {
		return tracertDelay;
	}

	public void setTracertDelay(Double tracertDelay) {
		this.tracertDelay = tracertDelay;
	}

	public Double getTracertDelayStd() {
		return tracertDelayStd;
	}

	public void setTracertDelayStd(Double tracertDelayStd) {
		this.tracertDelayStd = tracertDelayStd;
	}

	public Double getTracertDelayVar() {
		return tracertDelayVar;
	}

	public void setTracertDelayVar(Double tracertDelayVar) {
		this.tracertDelayVar = tracertDelayVar;
	}

	public Double getTracertJitter() {
		return tracertJitter;
	}

	public void setTracertJitter(Double tracertJitter) {
		this.tracertJitter = tracertJitter;
	}

	public Double getTracertJitterStd() {
		return tracertJitterStd;
	}

	public void setTracertJitterStd(Double tracertJitterStd) {
		this.tracertJitterStd = tracertJitterStd;
	}

	public Double getTracertJitterVar() {
		return tracertJitterVar;
	}

	public void setTracertJitterVar(Double tracertJitterVar) {
		this.tracertJitterVar = tracertJitterVar;
	}

	public Double getTracertLossRate() {
		return tracertLossRate;
	}

	public void setTracertLossRate(Double tracertLossRate) {
		this.tracertLossRate = tracertLossRate;
	}

	public Double getSlaDelay() {
		return slaDelay;
	}

	public void setSlaDelay(Double slaDelay) {
		this.slaDelay = slaDelay;
	}

	public Double getSlaGDelay() {
		return slaGDelay;
	}

	public void setSlaGDelay(Double slaGDelay) {
		this.slaGDelay = slaGDelay;
	}

	public Double getSlaRDelay() {
		return slaRDelay;
	}

	public void setSlaRDelay(Double slaRDelay) {
		this.slaRDelay = slaRDelay;
	}

	public Double getSlaJitter() {
		return slaJitter;
	}

	public void setSlaJitter(Double slaJitter) {
		this.slaJitter = slaJitter;
	}

	public Double getSlaGJitter() {
		return slaGJitter;
	}

	public void setSlaGJitter(Double slaGJitter) {
		this.slaGJitter = slaGJitter;
	}

	public Double getSlaRJitter() {
		return slaRJitter;
	}

	public void setSlaRJitter(Double slaRJitter) {
		this.slaRJitter = slaRJitter;
	}

	public Double getSlaLossRate() {
		return slaLossRate;
	}

	public void setSlaLossRate(Double slaLossRate) {
		this.slaLossRate = slaLossRate;
	}

	public Double getDnsDelay() {
		return dnsDelay;
	}

	public void setDnsDelay(Double dnsDelay) {
		this.dnsDelay = dnsDelay;
	}

	public Double getDnsSuccessRate() {
		return dnsSuccessRate;
	}

	public void setDnsSuccessRate(Double dnsSuccessRate) {
		this.dnsSuccessRate = dnsSuccessRate;
	}

	public Double getDhcpDelay() {
		return dhcpDelay;
	}

	public void setDhcpDelay(Double dhcpDelay) {
		this.dhcpDelay = dhcpDelay;
	}

	public Double getDhcpSuccessRate() {
		return dhcpSuccessRate;
	}

	public void setDhcpSuccessRate(Double dhcpSuccessRate) {
		this.dhcpSuccessRate = dhcpSuccessRate;
	}

	public Double getPppoeDelay() {
		return pppoeDelay;
	}

	public void setPppoeDelay(Double pppoeDelay) {
		this.pppoeDelay = pppoeDelay;
	}

	public Double getPppoeDropRate() {
		return pppoeDropRate;
	}

	public void setPppoeDropRate(Double pppoeDropRate) {
		this.pppoeDropRate = pppoeDropRate;
	}

	public Double getPppoeSuccessRate() {
		return pppoeSuccessRate;
	}

	public void setPppoeSuccessRate(Double pppoeSuccessRate) {
		this.pppoeSuccessRate = pppoeSuccessRate;
	}

	public Double getRadiusDelay() {
		return radiusDelay;
	}

	public void setRadiusDelay(Double radiusDelay) {
		this.radiusDelay = radiusDelay;
	}

	public Double getRadiusSuccessRate() {
		return radiusSuccessRate;
	}

	public void setRadiusSuccessRate(Double radiusSuccessRate) {
		this.radiusSuccessRate = radiusSuccessRate;
	}

	public Double getWebpageDnsDelay() {
		return webpageDnsDelay;
	}

	public void setWebpageDnsDelay(Double webpageDnsDelay) {
		this.webpageDnsDelay = webpageDnsDelay;
	}

	public Double getWebpageConnDelay() {
		return webpageConnDelay;
	}

	public void setWebpageConnDelay(Double webpageConnDelay) {
		this.webpageConnDelay = webpageConnDelay;
	}

	public Double getWebpageHeadbyteDelay() {
		return webpageHeadbyteDelay;
	}

	public void setWebpageHeadbyteDelay(Double webpageHeadbyteDelay) {
		this.webpageHeadbyteDelay = webpageHeadbyteDelay;
	}

	public Double getWebpagePageFileDelay() {
		return webpagePageFileDelay;
	}

	public void setWebpagePageFileDelay(Double webpagePageFileDelay) {
		this.webpagePageFileDelay = webpagePageFileDelay;
	}

	public Double getWebpageRedirectDelay() {
		return webpageRedirectDelay;
	}

	public void setWebpageRedirectDelay(Double webpageRedirectDelay) {
		this.webpageRedirectDelay = webpageRedirectDelay;
	}

	public Double getWebpageAboveFoldDelay() {
		return webpageAboveFoldDelay;
	}

	public void setWebpageAboveFoldDelay(Double webpageAboveFoldDelay) {
		this.webpageAboveFoldDelay = webpageAboveFoldDelay;
	}

	public Double getWebpagePageElementDelay() {
		return webpagePageElementDelay;
	}

	public void setWebpagePageElementDelay(Double webpagePageElementDelay) {
		this.webpagePageElementDelay = webpagePageElementDelay;
	}

	public Double getWebpageDownloadRate() {
		return webpageDownloadRate;
	}

	public void setWebpageDownloadRate(Double webpageDownloadRate) {
		this.webpageDownloadRate = webpageDownloadRate;
	}

	public Double getWebDownloadDnsDelay() {
		return webDownloadDnsDelay;
	}

	public void setWebDownloadDnsDelay(Double webDownloadDnsDelay) {
		this.webDownloadDnsDelay = webDownloadDnsDelay;
	}

	public Double getWebDownloadConnDelay() {
		return webDownloadConnDelay;
	}

	public void setWebDownloadConnDelay(Double webDownloadConnDelay) {
		this.webDownloadConnDelay = webDownloadConnDelay;
	}

	public Double getWebDownloadHeadbyteDelay() {
		return webDownloadHeadbyteDelay;
	}

	public void setWebDownloadHeadbyteDelay(Double webDownloadHeadbyteDelay) {
		this.webDownloadHeadbyteDelay = webDownloadHeadbyteDelay;
	}

	public Double getWebDownloadDownloadRate() {
		return webDownloadDownloadRate;
	}

	public void setWebDownloadDownloadRate(Double webDownloadDownloadRate) {
		this.webDownloadDownloadRate = webDownloadDownloadRate;
	}

	public Double getFtpDnsDelay() {
		return ftpDnsDelay;
	}

	public void setFtpDnsDelay(Double ftpDnsDelay) {
		this.ftpDnsDelay = ftpDnsDelay;
	}

	public Double getFtpConnDelay() {
		return ftpConnDelay;
	}

	public void setFtpConnDelay(Double ftpConnDelay) {
		this.ftpConnDelay = ftpConnDelay;
	}

	public Double getFtpLoginDelay() {
		return ftpLoginDelay;
	}

	public void setFtpLoginDelay(Double ftpLoginDelay) {
		this.ftpLoginDelay = ftpLoginDelay;
	}

	public Double getFtpHeadbyteDelay() {
		return ftpHeadbyteDelay;
	}

	public void setFtpHeadbyteDelay(Double ftpHeadbyteDelay) {
		this.ftpHeadbyteDelay = ftpHeadbyteDelay;
	}

	public Double getFtpDownloadRate() {
		return ftpDownloadRate;
	}

	public void setFtpDownloadRate(Double ftpDownloadRate) {
		this.ftpDownloadRate = ftpDownloadRate;
	}

	public Double getFtpUploadRate() {
		return ftpUploadRate;
	}

	public void setFtpUploadRate(Double ftpUploadRate) {
		this.ftpUploadRate = ftpUploadRate;
	}

	public Double getWebVideoDnsDelay() {
		return webVideoDnsDelay;
	}

	public void setWebVideoDnsDelay(Double webVideoDnsDelay) {
		this.webVideoDnsDelay = webVideoDnsDelay;
	}

	public Double getWebVideoWsConnDelay() {
		return webVideoWsConnDelay;
	}

	public void setWebVideoWsConnDelay(Double webVideoWsConnDelay) {
		this.webVideoWsConnDelay = webVideoWsConnDelay;
	}

	public Double getWebVideoWebPageDelay() {
		return webVideoWebPageDelay;
	}

	public void setWebVideoWebPageDelay(Double webVideoWebPageDelay) {
		this.webVideoWebPageDelay = webVideoWebPageDelay;
	}

	public Double getWebVideoSsConnDelay() {
		return webVideoSsConnDelay;
	}

	public void setWebVideoSsConnDelay(Double webVideoSsConnDelay) {
		this.webVideoSsConnDelay = webVideoSsConnDelay;
	}

	public Double getWebVideoAddressDelay() {
		return webVideoAddressDelay;
	}

	public void setWebVideoAddressDelay(Double webVideoAddressDelay) {
		this.webVideoAddressDelay = webVideoAddressDelay;
	}

	public Double getWebVideoMsConnDelay() {
		return webVideoMsConnDelay;
	}

	public void setWebVideoMsConnDelay(Double webVideoMsConnDelay) {
		this.webVideoMsConnDelay = webVideoMsConnDelay;
	}

	public Double getWebVideoHeadFrameDelay() {
		return webVideoHeadFrameDelay;
	}

	public void setWebVideoHeadFrameDelay(Double webVideoHeadFrameDelay) {
		this.webVideoHeadFrameDelay = webVideoHeadFrameDelay;
	}

	public Double getWebVideoInitBufferDelay() {
		return webVideoInitBufferDelay;
	}

	public void setWebVideoInitBufferDelay(Double webVideoInitBufferDelay) {
		this.webVideoInitBufferDelay = webVideoInitBufferDelay;
	}

	public Double getWebVideoLoadDelay() {
		return webVideoLoadDelay;
	}

	public void setWebVideoLoadDelay(Double webVideoLoadDelay) {
		this.webVideoLoadDelay = webVideoLoadDelay;
	}

	public Double getWebVideoTotalBufferDelay() {
		return webVideoTotalBufferDelay;
	}

	public void setWebVideoTotalBufferDelay(Double webVideoTotalBufferDelay) {
		this.webVideoTotalBufferDelay = webVideoTotalBufferDelay;
	}

	public Double getWebVideoDownloadRate() {
		return webVideoDownloadRate;
	}

	public void setWebVideoDownloadRate(Double webVideoDownloadRate) {
		this.webVideoDownloadRate = webVideoDownloadRate;
	}

	public Integer getWebVideoBufferTime() {
		return webVideoBufferTime;
	}

	public void setWebVideoBufferTime(Integer webVideoBufferTime) {
		this.webVideoBufferTime = webVideoBufferTime;
	}

	public Double getGameDnsDelay() {
		return gameDnsDelay;
	}

	public void setGameDnsDelay(Double gameDnsDelay) {
		this.gameDnsDelay = gameDnsDelay;
	}

	public Double getGameConnDelay() {
		return gameConnDelay;
	}

	public void setGameConnDelay(Double gameConnDelay) {
		this.gameConnDelay = gameConnDelay;
	}

	public Double getGamePacketDelay() {
		return gamePacketDelay;
	}

	public void setGamePacketDelay(Double gamePacketDelay) {
		this.gamePacketDelay = gamePacketDelay;
	}

	public Double getGamePacketJitter() {
		return gamePacketJitter;
	}

	public void setGamePacketJitter(Double gamePacketJitter) {
		this.gamePacketJitter = gamePacketJitter;
	}

	public Double getGameLossRate() {
		return gameLossRate;
	}

	public void setGameLossRate(Double gameLossRate) {
		this.gameLossRate = gameLossRate;
	}
}
