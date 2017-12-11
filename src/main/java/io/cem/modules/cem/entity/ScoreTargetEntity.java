package io.cem.modules.cem.entity;


public class ScoreTargetEntity {
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

    @Override
    public boolean equals(Object obj) {
        ScoreTargetEntity temp = (ScoreTargetEntity)obj;
        if(this.getCityId().equals(temp.getCityId())&&this.getCountyId().equals(temp.getCountyId())&&this.getProbeId().equals(temp.getProbeId())&&this.getTargetId().equals(temp.getTargetId())){
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {

        if(this.getCityId()!=null&&this.getCountyId()!=null&&this.getProbeId()!=null&&this.getTargetId()!=null)
            return this.getCityId()&this.getCountyId()&this.getProbeId()&this.getTargetId();
        return super.hashCode();
    }
}
