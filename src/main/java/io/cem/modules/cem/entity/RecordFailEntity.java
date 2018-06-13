package io.cem.modules.cem.entity;

import io.cem.common.utils.excel.annotation.ExcelIgnore;
import io.cem.common.utils.excel.annotation.ExportName;

import java.util.Date;
import java.util.Locale;

import static io.cem.modules.cem.entity.ScoreDateEntity.format;

public class RecordFailEntity {
    private static final long serialVersionUID = 1L;

    //记录ID
    private Integer id;
    //地市
    @ExcelIgnore
    private Integer cityId;
    //区县
    @ExcelIgnore
    private Integer countyId;
    //探针ID
    @ExcelIgnore
    private Integer probeId;
    //探针端口
    @ExportName(exportName = "端口")
    private String port;
    //任务ID
    @ExcelIgnore
    private Integer taskId;
    //子业务类型
    @ExcelIgnore
    private Integer serviceType;
    //测试目标ID
    @ExcelIgnore
    private Integer targetId;
    //测试目标IP
    @ExcelIgnore
    private Long targetIp;
    //记录日期
    @ExportName(exportName = "记录日期")
    private Date recordDate;
    //记录时间
    @ExportName(exportName = "记录时间")
    private String recordTime;
    @ExcelIgnore
    private Integer fail;
    @ExcelIgnore
    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Long getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(Long targetIp) {
        this.targetIp = targetIp;
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

    @Override
    public boolean equals(Object obj) {
        ScoreTargetEntity temp = (ScoreTargetEntity)obj;
        String end_time = format(this.getRecordDate(), "yyyy-MM-dd", Locale.CHINA);
        String end_time1 = format(temp.getRecordDate(), "yyyy-MM-dd", Locale.CHINA);
        if(this.getCityId().equals(temp.getCityId())&&this.getCountyId().equals(temp.getCountyId())&&this.getProbeId().equals(temp.getProbeId())&&end_time.equals(end_time1) && this.getRecordTime().equals(temp.getRecordTime())){
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        String end_time = format(this.getRecordDate(), "yyyyMMdd", Locale.CHINA);
        if(this.getCityId()!=null&&this.getCountyId()!=null&&this.getProbeId()!=null&&this.getRecordTime() != null && this.getRecordDate() != null)
            return this.getCityId()&this.getCountyId()&this.getProbeId()&this.recordTime.hashCode() & end_time.hashCode();
        return super.hashCode();
    }
}
