package io.cem.modules.cem.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间信息
 */
public class ScoreDateEntity {
    //地市
    private String cityName;
    //区县
    private String countyName;
    //探针ID
    private String probeName;
    //测试目标ID
    private String targetName;
    //地市id
    private Integer cityId;
    //区县
    private Integer countyId;
    //探针ID
    private Integer probeId;
    //测试目标ID
    private Integer targetId;
    //记录日期
    private Date recordDate;
    //记录时间
    private String recordTime;

    private Integer accessLayer;

    private String port;

    private Integer fail;

    private Integer total;

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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        ScoreDateEntity temp = (ScoreDateEntity) obj;
        String end_time = format(this.getRecordDate(), "yyyy-MM-dd", Locale.CHINA);
        String end_time1 = format(temp.getRecordDate(), "yyyy-MM-dd", Locale.CHINA);
        if (end_time.equals(end_time1) && this.getRecordTime().equals(temp.getRecordTime())) {
            return true;
        }
        return false;
    }

    //格式化指定类型的date​，返回String
    public static String format(Date date, String pattern, Locale locale) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }


    @Override
    public int hashCode() {
        String end_time = format(this.getRecordDate(), "yyyyMMdd", Locale.CHINA);
        if (this.getRecordTime() != null && this.getRecordDate() != null)
            return this.recordTime.hashCode() & end_time.hashCode();
        return super.hashCode();
    }

    public Integer getAccessLayer() {
        return accessLayer;
    }

    public void setAccessLayer(Integer accessLayer) {
        this.accessLayer = accessLayer;
    }
}

