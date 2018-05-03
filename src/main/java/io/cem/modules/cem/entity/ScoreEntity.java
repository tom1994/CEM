package io.cem.modules.cem.entity;

import io.cem.common.utils.excel.annotation.ExcelIgnore;
import io.cem.common.utils.excel.annotation.ExportName;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ScoreEntity {
    //记录ID
    @ExcelIgnore
    private Integer id;
    //地市
    @ExportName(exportName = "地市")
    private String cityName;
    @ExcelIgnore
    private Integer cityId;
    //区县
    @ExportName(exportName = "区县")
    private String countyName;
    @ExcelIgnore
    private Integer countyId;
    //探针ID
    @ExportName(exportName = "探针")
    private String probeName;
    @ExcelIgnore
    private Integer probeId;
    //子业务类型
    @ExcelIgnore
    private Integer serviceType;
    @ExportName(exportName = "端口")
    private String port;
    //测试目标ID
    @ExportName(exportName = "测试目标")
    private String targetName;
    @ExcelIgnore
    private Integer targetId;
    //记录日期
   // @ExportName(exportName = "记录日期")
    @ExcelIgnore
    private Date recordDate;
    //记录时间
   // @ExportName(exportName = "记录时间")
    @ExcelIgnore
    private String recordTime;
    //分数
    @ExportName(exportName = "分数")
    private Double score;
    //权重
    @ExcelIgnore
    private Double base;
    @ExcelIgnore
    private Integer accessLayer;
    @ExcelIgnore
    private Integer fail;
    @ExcelIgnore
    private Integer total;
    @ExportName(exportName = "出口名称")
    private String exit;

    /*ping ICMP details*/
    //时延平均值
    @ExcelIgnore
    private Double pingIcmpDelay;
    //时延标准差
    @ExcelIgnore
    private Double pingIcmpDelayStd;
    //时延方差
    @ExcelIgnore
    private Double pingIcmpDelayVar;
    //抖动平均值
    @ExcelIgnore
    private Double pingIcmpJitter;
    //抖动标准差
    @ExcelIgnore
    private Double pingIcmpJitterStd;
    //抖动方差
    @ExcelIgnore
    private Double pingIcmpJitterVar;
    //丢包率
    @ExcelIgnore
    private Double pingIcmpLossRate;

    /*ping TCP details*/
    //时延平均值
    @ExcelIgnore
    private Double pingTcpDelay;
    //时延标准差
    @ExcelIgnore
    private Double pingTcpDelayStd;
    //时延方差
    @ExcelIgnore
    private Double pingTcpDelayVar;
    //抖动平均值
    @ExcelIgnore
    private Double pingTcpJitter;
    //抖动标准差
    @ExcelIgnore
    private Double pingTcpJitterStd;
    //抖动方差
    @ExcelIgnore
    private Double pingTcpJitterVar;
    //丢包率
    @ExcelIgnore
    private Double pingTcpLossRate;

    /*ping UDP details*/
    //时延平均值
    @ExcelIgnore
    private Double pingUdpDelay;
    //时延标准差
    @ExcelIgnore
    private Double pingUdpDelayStd;
    //时延方差
    @ExcelIgnore
    private Double pingUdpDelayVar;
    //抖动平均值
    @ExcelIgnore
    private Double pingUdpJitter;
    //抖动标准差
    @ExcelIgnore
    private Double pingUdpJitterStd;
    //抖动方差
    @ExcelIgnore
    private Double pingUdpJitterVar;
    //丢包率
    @ExcelIgnore
    private Double pingUdpLossRate;

    /*tracert route ICMP details*/
    //时延平均值
    @ExcelIgnore
    private Double tracertIcmpDelay;
    //时延标准差
    @ExcelIgnore
    private Double tracertIcmpDelayStd;
    //时延方差
    @ExcelIgnore
    private Double tracertIcmpDelayVar;
    //抖动平均值
    @ExcelIgnore
    private Double tracertIcmpJitter;
    //抖动标准差
    @ExcelIgnore
    private Double tracertIcmpJitterStd;
    //抖动方差
    @ExcelIgnore
    private Double tracertIcmpJitterVar;
    //丢包率
    @ExcelIgnore
    private Double tracertIcmpLossRate;

    /*tracert route TCP details*/
    //时延平均值
    @ExcelIgnore
    private Double tracertTcpDelay;
    //时延标准差
    @ExcelIgnore
    private Double tracertTcpDelayStd;
    //时延方差
    @ExcelIgnore
    private Double tracertTcpDelayVar;
    //抖动平均值
    @ExcelIgnore
    private Double tracertTcpJitter;
    //抖动标准差
    @ExcelIgnore
    private Double tracertTcpJitterStd;
    //抖动方差
    @ExcelIgnore
    private Double tracertTcpJitterVar;
    //丢包率
    @ExcelIgnore
    private Double tracertTcpLossRate;

    /*sla tcp details*/
    //时延平均值
    @ExcelIgnore
    private Double slaTcpDelay;
    //往向时延
    @ExcelIgnore
    private Double slaTcpGDelay;
    //返向时延
    @ExcelIgnore
    private Double slaTcpRDelay;
    //抖动平均值
    @ExcelIgnore
    private Double slaTcpJitter;
    //往向抖动
    @ExcelIgnore
    private Double slaTcpGJitter;
    //返向抖动
    @ExcelIgnore
    private Double slaTcpRJitter;
    //丢包率
    @ExcelIgnore
    private Double slaTcpLossRate;

    /*sla UDP details*/
    //时延平均值
    @ExcelIgnore
    private Double slaUdpDelay;
    //往向时延
    @ExcelIgnore
    private Double slaUdpGDelay;
    //返向时延
    @ExcelIgnore
    private Double slaUdpRDelay;
    //抖动平均值
    @ExcelIgnore
    private Double slaUdpJitter;
    //往向抖动
    @ExcelIgnore
    private Double slaUdpGJitter;
    //返向抖动
    @ExcelIgnore
    private Double slaUdpRJitter;
    //丢包率
    @ExcelIgnore
    private Double slaUdpLossRate;

    /*dns details*/
    //时延平均值
    @ExcelIgnore
    private Double dnsDelay;
    //查询成功率
    @ExcelIgnore
    private Double dnsSuccessRate;

    /*dhcp details*/
    //时延平均值
    @ExcelIgnore
    private Double dhcpDelay;
    //查询成功率
    @ExcelIgnore
    private Double dhcpSuccessRate;

    /*pppoe details*/
    //时延平均值
    @ExcelIgnore
    private Double pppoeDelay;
    //掉线率
    @ExcelIgnore
    private Double pppoeDropRate;
    //查询成功率
    @ExcelIgnore
    private Double pppoeSuccessRate;

    /*radius details*/
    //时延平均值
    @ExcelIgnore
    private Double radiusDelay;
    //认证成功率
    @ExcelIgnore
    private Double radiusSuccessRate;

    /*web page details*/
    //DNS时延
    @ExcelIgnore
    private Double webpageDnsDelay;
    //连接时延
    @ExcelIgnore
    private Double webpageConnDelay;
    //首字节时延
    @ExcelIgnore
    private Double webpageHeadbyteDelay;
    //页面文件时延
    @ExcelIgnore
    private Double webpagePageFileDelay;
    //重定向时延
    @ExcelIgnore
    private Double webpageRedirectDelay;
    //首屏时延
    @ExcelIgnore
    private Double webpageAboveFoldDelay;
    //页面元素时延
    @ExcelIgnore
    private Double webpagePageElementDelay;
    //页面加载时延
    @ExcelIgnore
    private Double webpageLoadDelay;
    //下载速率
    @ExcelIgnore
    private Double webpageDownloadRate;

    /*web download details*/
    //DNS时延
    @ExcelIgnore
    private Double webDownloadDnsDelay;
    //连接时延
    @ExcelIgnore
    private Double webDownloadConnDelay;
    //首字节时延
    @ExcelIgnore
    private Double webDownloadHeadbyteDelay;
    //下载速率
    @ExcelIgnore
    private Double webDownloadDownloadRate;

    /*ftp download details*/
    //DNS时延
    @ExcelIgnore
    private Double ftpDownloadDnsDelay;
    //连接时延
    @ExcelIgnore
    private Double ftpDownloadConnDelay;
    //登录时延
    @ExcelIgnore
    private Double ftpDownloadLoginDelay;
    //首字节时延
    @ExcelIgnore
    private Double ftpDownloadHeadbyteDelay;
    //下载速率
    @ExcelIgnore
    private Double ftpDownloadDownloadRate;

    /*ftp upload details*/
    //DNS时延
    @ExcelIgnore
    private Double ftpUploadDnsDelay;
    //连接时延
    @ExcelIgnore
    private Double ftpUploadConnDelay;
    //登录时延
    @ExcelIgnore
    private Double ftpUploadLoginDelay;
    //首字节时延
    @ExcelIgnore
    private Double ftpUploadHeadbyteDelay;
    //上传速率
    @ExcelIgnore
    private Double ftpUploadUploadRate;

    /*web video details*/
    //DNS时延
    @ExcelIgnore
    private Double webVideoDnsDelay;
    //连接WEB服务器时延
    @ExcelIgnore
    private Double webVideoWsConnDelay;
    //WEB页面时延
    @ExcelIgnore
    private Double webVideoWebPageDelay;
    //连接调度服务器时延
    @ExcelIgnore
    private Double webVideoSsConnDelay;
    //获取视频地址时延
    @ExcelIgnore
    private Double webVideoAddressDelay;
    //连接媒体服务器时延
    @ExcelIgnore
    private Double webVideoMsConnDelay;
    //首帧时延
    @ExcelIgnore
    private Double webVideoHeadFrameDelay;
    //首次缓冲时延
    @ExcelIgnore
    private Double webVideoInitBufferDelay;
    //视频加载时延
    @ExcelIgnore
    private Double webVideoLoadDelay;
    //总体缓冲时间
    @ExcelIgnore
    private Double webVideoTotalBufferDelay;
    //下载速率
    @ExcelIgnore
    private Double webVideoDownloadRate;
    //缓冲次数
    @ExcelIgnore
    private Integer webVideoBufferTime;

    /*game details*/
    //DNS时延
    @ExcelIgnore
    private Double gameDnsDelay;
    //连接时延
    @ExcelIgnore
    private Double gameConnDelay;
    //游戏数据包时延
    @ExcelIgnore
    private Double gamePacketDelay;
    //游戏数据包抖动
    @ExcelIgnore
    private Double gamePacketJitter;
    //游戏数据包丢包率
    @ExcelIgnore
    private Double gameLossRate;
    //网络连通性业务分数
    @ExcelIgnore
    private Double connectionScore;
    //网络层质量业务分数
    @ExcelIgnore
    private Double qualityScore;
    //网页浏览业务分数
    @ExcelIgnore
    private Double broswerScore;
    //文件下载业务分数
    @ExcelIgnore
    private Double downloadScore;
    //在线视频业务分数
    @ExcelIgnore
    private Double videoScore;
    //网络游戏业务分数
    @ExcelIgnore
    private Double gameScore;


    //网络连通性业务分数
    @ExcelIgnore
    private Double icmpPingScore;
    @ExcelIgnore
    private Double tcpPingScore;
    @ExcelIgnore
    private Double udpPingScore;
    @ExcelIgnore
    private Double icmpTracertScore;
    @ExcelIgnore
    private Double udpTracertScore;
    //网络层质量业务分数
    @ExcelIgnore
    private Double tcpSlaScore;
    @ExcelIgnore
    private Double udpSlaScore;
    @ExcelIgnore
    private Double pppoeScore;
    @ExcelIgnore
    private Double dhcpScore;
    @ExcelIgnore
    private Double dnsScore;
    @ExcelIgnore
    private Double radiusScore;
    //文件下载业务分数
    @ExcelIgnore
    private Double webDownloadScore;
    @ExcelIgnore
    private Double ftpDownloadScore;
    @ExcelIgnore
    private Double ftpUploadScore;
    //网页浏览业务分数
    @ExcelIgnore
    private Double webPageScore;
    //在线视频分数
    @ExcelIgnore
    private Double webVideoScore;
    //在线游戏分数
    @ExcelIgnore
    private Double onlineGameScore;





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
     * 设置：分数
     */
    public Double getScore() {
        DecimalFormat df = new DecimalFormat("#.00");
        score = Double.parseDouble(df.format(score));
        return score;
    }
    /**
     * 获取：分数
     */
    public void setScore(Double score) {
        this.score = score;
    }
    /**
     * 设置：权重
     */
    public Double getBase() {
        return base;
    }
    /**
     * 获取：权重
     */
    public void setBase(Double base) {
        this.base = base;
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

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getProbeId() {
        return probeId;
    }

    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getAccessLayer() {
        return accessLayer;
    }

    public void setAccessLayer(Integer accessLayer) {
        this.accessLayer = accessLayer;
    }

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

    public Double getPingIcmpDelay() {
        return pingIcmpDelay;
    }

    public void setPingIcmpDelay(Double pingIcmpDelay) {
        this.pingIcmpDelay = pingIcmpDelay;
    }

    public Double getPingIcmpDelayStd() {
        return pingIcmpDelayStd;
    }

    public void setPingIcmpDelayStd(Double pingIcmpDelayStd) {
        this.pingIcmpDelayStd = pingIcmpDelayStd;
    }

    public Double getPingIcmpDelayVar() {
        return pingIcmpDelayVar;
    }

    public void setPingIcmpDelayVar(Double pingIcmpDelayVar) {
        this.pingIcmpDelayVar = pingIcmpDelayVar;
    }

    public Double getPingIcmpJitter() {
        return pingIcmpJitter;
    }

    public void setPingIcmpJitter(Double pingIcmpJitter) {
        this.pingIcmpJitter = pingIcmpJitter;
    }

    public Double getPingIcmpJitterStd() {
        return pingIcmpJitterStd;
    }

    public void setPingIcmpJitterStd(Double pingIcmpJitterStd) {
        this.pingIcmpJitterStd = pingIcmpJitterStd;
    }

    public Double getPingIcmpJitterVar() {
        return pingIcmpJitterVar;
    }

    public void setPingIcmpJitterVar(Double pingIcmpJitterVar) {
        this.pingIcmpJitterVar = pingIcmpJitterVar;
    }

    public Double getPingIcmpLossRate() {
        return pingIcmpLossRate;
    }

    public void setPingIcmpLossRate(Double pingIcmpLossRate) {
        this.pingIcmpLossRate = pingIcmpLossRate;
    }

    public Double getPingTcpDelay() {
        return pingTcpDelay;
    }

    public void setPingTcpDelay(Double pingTcpDelay) {
        this.pingTcpDelay = pingTcpDelay;
    }

    public Double getPingTcpDelayStd() {
        return pingTcpDelayStd;
    }

    public void setPingTcpDelayStd(Double pingTcpDelayStd) {
        this.pingTcpDelayStd = pingTcpDelayStd;
    }

    public Double getPingTcpDelayVar() {
        return pingTcpDelayVar;
    }

    public void setPingTcpDelayVar(Double pingTcpDelayVar) {
        this.pingTcpDelayVar = pingTcpDelayVar;
    }

    public Double getPingTcpJitter() {
        return pingTcpJitter;
    }

    public void setPingTcpJitter(Double pingTcpJitter) {
        this.pingTcpJitter = pingTcpJitter;
    }

    public Double getPingTcpJitterStd() {
        return pingTcpJitterStd;
    }

    public void setPingTcpJitterStd(Double pingTcpJitterStd) {
        this.pingTcpJitterStd = pingTcpJitterStd;
    }

    public Double getPingTcpJitterVar() {
        return pingTcpJitterVar;
    }

    public void setPingTcpJitterVar(Double pingTcpJitterVar) {
        this.pingTcpJitterVar = pingTcpJitterVar;
    }

    public Double getPingTcpLossRate() {
        return pingTcpLossRate;
    }

    public void setPingTcpLossRate(Double pingTcpLossRate) {
        this.pingTcpLossRate = pingTcpLossRate;
    }

    public Double getPingUdpDelay() {
        return pingUdpDelay;
    }

    public void setPingUdpDelay(Double pingUdpDelay) {
        this.pingUdpDelay = pingUdpDelay;
    }

    public Double getPingUdpDelayStd() {
        return pingUdpDelayStd;
    }

    public void setPingUdpDelayStd(Double pingUdpDelayStd) {
        this.pingUdpDelayStd = pingUdpDelayStd;
    }

    public Double getPingUdpDelayVar() {
        return pingUdpDelayVar;
    }

    public void setPingUdpDelayVar(Double pingUdpDelayVar) {
        this.pingUdpDelayVar = pingUdpDelayVar;
    }

    public Double getPingUdpJitter() {
        return pingUdpJitter;
    }

    public void setPingUdpJitter(Double pingUdpJitter) {
        this.pingUdpJitter = pingUdpJitter;
    }

    public Double getPingUdpJitterStd() {
        return pingUdpJitterStd;
    }

    public void setPingUdpJitterStd(Double pingUdpJitterStd) {
        this.pingUdpJitterStd = pingUdpJitterStd;
    }

    public Double getPingUdpJitterVar() {
        return pingUdpJitterVar;
    }

    public void setPingUdpJitterVar(Double pingUdpJitterVar) {
        this.pingUdpJitterVar = pingUdpJitterVar;
    }

    public Double getPingUdpLossRate() {
        return pingUdpLossRate;
    }

    public void setPingUdpLossRate(Double pingUdpLossRate) {
        this.pingUdpLossRate = pingUdpLossRate;
    }

    public Double getTracertIcmpDelay() {
        return tracertIcmpDelay;
    }

    public void setTracertIcmpDelay(Double tracertIcmpDelay) {
        this.tracertIcmpDelay = tracertIcmpDelay;
    }

    public Double getTracertIcmpDelayStd() {
        return tracertIcmpDelayStd;
    }

    public void setTracertIcmpDelayStd(Double tracertIcmpDelayStd) {
        this.tracertIcmpDelayStd = tracertIcmpDelayStd;
    }

    public Double getTracertIcmpDelayVar() {
        return tracertIcmpDelayVar;
    }

    public void setTracertIcmpDelayVar(Double tracertIcmpDelayVar) {
        this.tracertIcmpDelayVar = tracertIcmpDelayVar;
    }

    public Double getTracertIcmpJitter() {
        return tracertIcmpJitter;
    }

    public void setTracertIcmpJitter(Double tracertIcmpJitter) {
        this.tracertIcmpJitter = tracertIcmpJitter;
    }

    public Double getTracertIcmpJitterStd() {
        return tracertIcmpJitterStd;
    }

    public void setTracertIcmpJitterStd(Double tracertIcmpJitterStd) {
        this.tracertIcmpJitterStd = tracertIcmpJitterStd;
    }

    public Double getTracertIcmpJitterVar() {
        return tracertIcmpJitterVar;
    }

    public void setTracertIcmpJitterVar(Double tracertIcmpJitterVar) {
        this.tracertIcmpJitterVar = tracertIcmpJitterVar;
    }

    public Double getTracertIcmpLossRate() {
        return tracertIcmpLossRate;
    }

    public void setTracertIcmpLossRate(Double tracertIcmpLossRate) {
        this.tracertIcmpLossRate = tracertIcmpLossRate;
    }

    public Double getTracertTcpDelay() {
        return tracertTcpDelay;
    }

    public void setTracertTcpDelay(Double tracertTcpDelay) {
        this.tracertTcpDelay = tracertTcpDelay;
    }

    public Double getTracertTcpDelayStd() {
        return tracertTcpDelayStd;
    }

    public void setTracertTcpDelayStd(Double tracertTcpDelayStd) {
        this.tracertTcpDelayStd = tracertTcpDelayStd;
    }

    public Double getTracertTcpDelayVar() {
        return tracertTcpDelayVar;
    }

    public void setTracertTcpDelayVar(Double tracertTcpDelayVar) {
        this.tracertTcpDelayVar = tracertTcpDelayVar;
    }

    public Double getTracertTcpJitter() {
        return tracertTcpJitter;
    }

    public void setTracertTcpJitter(Double tracertTcpJitter) {
        this.tracertTcpJitter = tracertTcpJitter;
    }

    public Double getTracertTcpJitterStd() {
        return tracertTcpJitterStd;
    }

    public void setTracertTcpJitterStd(Double tracertTcpJitterStd) {
        this.tracertTcpJitterStd = tracertTcpJitterStd;
    }

    public Double getTracertTcpJitterVar() {
        return tracertTcpJitterVar;
    }

    public void setTracertTcpJitterVar(Double tracertTcpJitterVar) {
        this.tracertTcpJitterVar = tracertTcpJitterVar;
    }

    public Double getTracertTcpLossRate() {
        return tracertTcpLossRate;
    }

    public void setTracertTcpLossRate(Double tracertTcpLossRate) {
        this.tracertTcpLossRate = tracertTcpLossRate;
    }

    public Double getSlaTcpDelay() {
        return slaTcpDelay;
    }

    public void setSlaTcpDelay(Double slaTcpDelay) {
        this.slaTcpDelay = slaTcpDelay;
    }

    public Double getSlaTcpGDelay() {
        return slaTcpGDelay;
    }

    public void setSlaTcpGDelay(Double slaTcpGDelay) {
        this.slaTcpGDelay = slaTcpGDelay;
    }

    public Double getSlaTcpRDelay() {
        return slaTcpRDelay;
    }

    public void setSlaTcpRDelay(Double slaTcpRDelay) {
        this.slaTcpRDelay = slaTcpRDelay;
    }

    public Double getSlaTcpJitter() {
        return slaTcpJitter;
    }

    public void setSlaTcpJitter(Double slaTcpJitter) {
        this.slaTcpJitter = slaTcpJitter;
    }

    public Double getSlaTcpGJitter() {
        return slaTcpGJitter;
    }

    public void setSlaTcpGJitter(Double slaTcpGJitter) {
        this.slaTcpGJitter = slaTcpGJitter;
    }

    public Double getSlaTcpRJitter() {
        return slaTcpRJitter;
    }

    public void setSlaTcpRJitter(Double slaTcpRJitter) {
        this.slaTcpRJitter = slaTcpRJitter;
    }

    public Double getSlaTcpLossRate() {
        return slaTcpLossRate;
    }

    public void setSlaTcpLossRate(Double slaTcpLossRate) {
        this.slaTcpLossRate = slaTcpLossRate;
    }

    public Double getSlaUdpDelay() {
        return slaUdpDelay;
    }

    public void setSlaUdpDelay(Double slaUdpDelay) {
        this.slaUdpDelay = slaUdpDelay;
    }

    public Double getSlaUdpGDelay() {
        return slaUdpGDelay;
    }

    public void setSlaUdpGDelay(Double slaUdpGDelay) {
        this.slaUdpGDelay = slaUdpGDelay;
    }

    public Double getSlaUdpRDelay() {
        return slaUdpRDelay;
    }

    public void setSlaUdpRDelay(Double slaUdpRDelay) {
        this.slaUdpRDelay = slaUdpRDelay;
    }

    public Double getSlaUdpJitter() {
        return slaUdpJitter;
    }

    public void setSlaUdpJitter(Double slaUdpJitter) {
        this.slaUdpJitter = slaUdpJitter;
    }

    public Double getSlaUdpGJitter() {
        return slaUdpGJitter;
    }

    public void setSlaUdpGJitter(Double slaUdpGJitter) {
        this.slaUdpGJitter = slaUdpGJitter;
    }

    public Double getSlaUdpRJitter() {
        return slaUdpRJitter;
    }

    public void setSlaUdpRJitter(Double slaUdpRJitter) {
        this.slaUdpRJitter = slaUdpRJitter;
    }

    public Double getSlaUdpLossRate() {
        return slaUdpLossRate;
    }

    public void setSlaUdpLossRate(Double slaUdpLossRate) {
        this.slaUdpLossRate = slaUdpLossRate;
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

    public Double getFtpDownloadDnsDelay() {
        return ftpDownloadDnsDelay;
    }

    public void setFtpDownloadDnsDelay(Double ftpDownloadDnsDelay) {
        this.ftpDownloadDnsDelay = ftpDownloadDnsDelay;
    }

    public Double getFtpDownloadConnDelay() {
        return ftpDownloadConnDelay;
    }

    public void setFtpDownloadConnDelay(Double ftpDownloadConnDelay) {
        this.ftpDownloadConnDelay = ftpDownloadConnDelay;
    }

    public Double getFtpDownloadLoginDelay() {
        return ftpDownloadLoginDelay;
    }

    public void setFtpDownloadLoginDelay(Double ftpDownloadLoginDelay) {
        this.ftpDownloadLoginDelay = ftpDownloadLoginDelay;
    }

    public Double getFtpDownloadHeadbyteDelay() {
        return ftpDownloadHeadbyteDelay;
    }

    public void setFtpDownloadHeadbyteDelay(Double ftpDownloadHeadbyteDelay) {
        this.ftpDownloadHeadbyteDelay = ftpDownloadHeadbyteDelay;
    }

    public Double getFtpDownloadDownloadRate() {
        return ftpDownloadDownloadRate;
    }

    public void setFtpDownloadDownloadRate(Double ftpDownloadDownloadRate) {
        this.ftpDownloadDownloadRate = ftpDownloadDownloadRate;
    }

    public Double getFtpUploadDnsDelay() {
        return ftpUploadDnsDelay;
    }

    public void setFtpUploadDnsDelay(Double ftpUploadDnsDelay) {
        this.ftpUploadDnsDelay = ftpUploadDnsDelay;
    }

    public Double getFtpUploadConnDelay() {
        return ftpUploadConnDelay;
    }

    public void setFtpUploadConnDelay(Double ftpUploadConnDelay) {
        this.ftpUploadConnDelay = ftpUploadConnDelay;
    }

    public Double getFtpUploadLoginDelay() {
        return ftpUploadLoginDelay;
    }

    public void setFtpUploadLoginDelay(Double ftpUploadLoginDelay) {
        this.ftpUploadLoginDelay = ftpUploadLoginDelay;
    }

    public Double getFtpUploadHeadbyteDelay() {
        return ftpUploadHeadbyteDelay;
    }

    public void setFtpUploadHeadbyteDelay(Double ftpUploadHeadbyteDelay) {
        this.ftpUploadHeadbyteDelay = ftpUploadHeadbyteDelay;
    }

    public Double getFtpUploadUploadRate() {
        return ftpUploadUploadRate;
    }

    public void setFtpUploadUploadRate(Double ftpUploadUploadRate) {
        this.ftpUploadUploadRate = ftpUploadUploadRate;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public Double getConnectionScore() {
        return connectionScore;
    }

    public void setConnectionScore(Double connectionScore) {
        this.connectionScore = connectionScore;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Double getBroswerScore() {
        return broswerScore;
    }

    public void setBroswerScore(Double broswerScore) {
        this.broswerScore = broswerScore;
    }

    public Double getDownloadScore() {
        return downloadScore;
    }

    public void setDownloadScore(Double downloadScore) {
        this.downloadScore = downloadScore;
    }

    public Double getVideoScore() {
        return videoScore;
    }

    public void setVideoScore(Double videoScore) {
        this.videoScore = videoScore;
    }

    public Double getGameScore() {
        return gameScore;
    }

    public void setGameScore(Double gameScore) {
        this.gameScore = gameScore;
    }

    public Double getIcmpPingScore() {
        return icmpPingScore;
    }

    public void setIcmpPingScore(Double icmpPingScore) {
        this.icmpPingScore = icmpPingScore;
    }

    public Double getTcpPingScore() {
        return tcpPingScore;
    }

    public void setTcpPingScore(Double tcpPingScore) {
        this.tcpPingScore = tcpPingScore;
    }

    public Double getUdpPingScore() {
        return udpPingScore;
    }

    public void setUdpPingScore(Double udpPingScore) {
        this.udpPingScore = udpPingScore;
    }

    public Double getIcmpTracertScore() {
        return icmpTracertScore;
    }

    public void setIcmpTracertScore(Double icmpTracertScore) {
        this.icmpTracertScore = icmpTracertScore;
    }

    public Double getUdpTracertScore() {
        return udpTracertScore;
    }

    public void setUdpTracertScore(Double udpTracertScore) {
        this.udpTracertScore = udpTracertScore;
    }

    public Double getTcpSlaScore() {
        return tcpSlaScore;
    }

    public void setTcpSlaScore(Double tcpSlaScore) {
        this.tcpSlaScore = tcpSlaScore;
    }

    public Double getUdpSlaScore() {
        return udpSlaScore;
    }

    public void setUdpSlaScore(Double udpSlaScore) {
        this.udpSlaScore = udpSlaScore;
    }

    public Double getPppoeScore() {
        return pppoeScore;
    }

    public void setPppoeScore(Double pppoeScore) {
        this.pppoeScore = pppoeScore;
    }

    public Double getDhcpScore() {
        return dhcpScore;
    }

    public void setDhcpScore(Double dhcpScore) {
        this.dhcpScore = dhcpScore;
    }

    public Double getDnsScore() {
        return dnsScore;
    }

    public void setDnsScore(Double dnsScore) {
        this.dnsScore = dnsScore;
    }

    public Double getRadiusScore() {
        return radiusScore;
    }

    public void setRadiusScore(Double radiusScore) {
        this.radiusScore = radiusScore;
    }

    public Double getWebDownloadScore() {
        return webDownloadScore;
    }

    public void setWebDownloadScore(Double webDownloadScore) {
        this.webDownloadScore = webDownloadScore;
    }

    public Double getFtpDownloadScore() {
        return ftpDownloadScore;
    }

    public void setFtpDownloadScore(Double ftpDownloadScore) {
        this.ftpDownloadScore = ftpDownloadScore;
    }

    public Double getFtpUploadScore() {
        return ftpUploadScore;
    }

    public void setFtpUploadScore(Double ftpUploadScore) {
        this.ftpUploadScore = ftpUploadScore;
    }

    public Double getWebPageScore() {
        return webPageScore;
    }

    public void setWebPageScore(Double webPageScore) {
        this.webPageScore = webPageScore;
    }

    public Double getWebVideoScore() {
        return webVideoScore;
    }

    public void setWebVideoScore(Double webVideoScore) {
        this.webVideoScore = webVideoScore;
    }

    public Double getOnlineGameScore() {
        return onlineGameScore;
    }

    public void setOnlineGameScore(Double onlineGameScore) {
        this.onlineGameScore = onlineGameScore;
    }

    public Double getWebpageLoadDelay() {
        return webpageLoadDelay;
    }

    public void setWebpageLoadDelay(Double webpageLoadDelay) {
        this.webpageLoadDelay = webpageLoadDelay;
    }
    //    public static Comparator scoreComparator = new Comparator() {
//        @Override
//        public int compare(Object o1, Object o2) {
//            return ( ((ScoreEntity) o1).getScore() > ((ScoreEntity) o2).getScore() ? -1 :
//                    (((ScoreEntity) o1).getScore() ==((ScoreEntity) o2).getScore() ? 0 : 1));
//        }
//    };

    @SuppressWarnings("unchecked")
    public static void sortStringMethod(List<ScoreEntity> list){
        Collections.sort(list, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                ScoreEntity stu1=(ScoreEntity) o1;
                ScoreEntity stu2=(ScoreEntity) o2;
                return stu2.getScore().compareTo(stu1.getScore());
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void sortDescStringMethod(List<ScoreEntity> list){
        Collections.sort(list, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                ScoreEntity stu1=(ScoreEntity) o1;
                ScoreEntity stu2=(ScoreEntity) o2;
                return stu1.getScore().compareTo(stu2.getScore());
            }
        });
    }

}
