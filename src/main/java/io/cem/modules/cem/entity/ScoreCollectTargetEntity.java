package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 测试目标评分表
 */
public class ScoreCollectTargetEntity implements Serializable {
    private Integer id;
    private Double score;
    private int target;
    private String targetName;
    private Integer serviceType;
    private Date scoreDate;
    private String scoreTime;

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }

    public Integer getId() {
        return id;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String toString(){
        return "id:"+id+";score:"+score+
        ";score:"+score+
        ";target:"+target+
        ";targetName:"+targetName+
        ";serviceType:"+serviceType+
                ";scoreDate:"+scoreDate+
                ";scoreTime:"+scoreTime;

    }
    public static void main(String args[]){
        ScoreCollectTargetEntity a = new ScoreCollectTargetEntity();
        a.setScoreDate(new Date());
        a.setTargetName("36aa");
        a.setScore(69d);
        a.setScoreTime("12:03:41");
        System.out.println(a);
    }

}
