package io.cem.modules.cem.entity;

public class ScoreTargetEntity {
    //地市
    private Integer cityId;
    //区县
    private Integer countyId;
    //探针ID
    private Integer probeId;
    //测试目标ID
    private Integer targetId;

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


    public boolean equals(ScoreEntity obj) {
        Boolean equal = false;
        if(((this.getCityId())==(obj.getCityId()))&&((this.getCountyId())==(obj.getCountyId()))&&((this.getProbeId())==obj.getProbeId())&&((this.getTargetId())==obj.getTargetId())){
            equal=true;
        }else{}
        return equal;
    }


    public int hashCode() {
        return super.hashCode();
    }
}
