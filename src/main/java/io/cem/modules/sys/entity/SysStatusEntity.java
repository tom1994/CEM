package io.cem.modules.sys.entity;

import java.math.BigDecimal;

/*
*
* 系统参数实体类
* 参考表 system_status
 */
public class SysStatusEntity {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String hostName;
    private String os;
    private Long memUsed;
    private Long memTotal;
    private Double memUsedPercent;
    private Long diskUsed;
    private Long diskTotal;
    private Double diskUsedPercent;
    private Double cpuPercent;
    private Double cpuPercentDecimal2;
    private String baseboardSerial;
    private Long swapUsed;
    private Long swapTotal;
    private Double swapUsedPercent;

    private Double memUsedGB;
    private Double memTotalGB;
    private Double diskUsedGB;
    private Double diskTotalGB;
    private Double swapUsedGB;
    private Double swapTotalGB;




    public Integer getId() {
        return id;
    }

    public String getHostName() {
        return hostName;
    }

    public String getOs() {
        return os;
    }

    public Long getMemUsed() {
        return memUsed;
    }

    public Long getMemTotal() {
        return memTotal;
    }

    public Long getDiskUsed() {
        return diskUsed;
    }

    public Long getDiskTotal() {
        return diskTotal;
    }

    public Double getCpuPercent() {
        return cpuPercent;
    }

    public String getBaseboardSerial() {
        return baseboardSerial;
    }

    public Long getSwapUsed() {
        return swapUsed;
    }

    public Long getSwapTotal() {
        return swapTotal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setMemUsed(Long memUsed) {
        this.memUsed = memUsed;
    }

    public void setMemTotal(Long memTotal) {
        this.memTotal = memTotal;
    }

    public void setDiskUsed(Long diskUsed) {
        this.diskUsed = diskUsed;
    }

    public void setDiskTotal(Long diskTotal) {
        this.diskTotal = diskTotal;
    }

    public void setCpuPercent(Double cpuPercent) {
        this.cpuPercent = cpuPercent;
    }

    public void setBaseboardSerial(String baseboardSerial) {
        this.baseboardSerial = baseboardSerial;
    }

    public void setSwapUsed(Long swapUsed) {
        this.swapUsed = swapUsed;
    }

    public void setSwapTotal(Long swapTotal) {
        this.swapTotal = swapTotal;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private final static  BigDecimal GB_VALUE = new BigDecimal(1024);

    public Double getMemUsedGB() {
        memUsedGB = new BigDecimal(memUsed)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return memUsedGB;
    }

    public Double getMemTotalGB() {
        memTotalGB = new BigDecimal(memTotal)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return memTotalGB;
    }

    public Double getDiskUsedGB() {
        diskUsedGB = new BigDecimal(diskUsed)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return diskUsedGB;
    }

    public Double getDiskTotalGB() {
        diskTotalGB = new BigDecimal(diskTotal)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return diskTotalGB;
    }

    public Double getSwapUsedGB() {
        swapUsedGB = new BigDecimal(swapUsed)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return swapUsedGB;
    }

    public Double getSwapTotalGB() {
        swapTotalGB = new BigDecimal(swapTotal)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,BigDecimal.ROUND_HALF_UP)
                .divide(GB_VALUE,2,BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return swapTotalGB;
    }

    public Double getMemUsedPercent() {
        BigDecimal a = new BigDecimal(memTotal);
        BigDecimal b = new BigDecimal(memUsed);
        memUsedPercent = b.divide(a,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
        return memUsedPercent;
    }

    public Double getDiskUsedPercent() {
        BigDecimal a = new BigDecimal(diskTotal);
        BigDecimal b = new BigDecimal(diskUsed);
        BigDecimal r = b.divide(a,4,BigDecimal.ROUND_HALF_UP);
        diskUsedPercent = r.multiply(new BigDecimal(100)).doubleValue();
        return diskUsedPercent;
    }

    public Double getCpuPercentDecimal2() {
        BigDecimal b = new BigDecimal(cpuPercent);
        cpuPercentDecimal2 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return cpuPercentDecimal2;
    }

    public Double getSwapUsedPercent() {
        BigDecimal a = new BigDecimal(swapTotal);
        BigDecimal b = new BigDecimal(swapUsed);
        BigDecimal r = b.divide(a,4,BigDecimal.ROUND_HALF_UP);
        swapUsedPercent = r.multiply(new BigDecimal(100)).doubleValue();
        return swapUsedPercent;
    }



    public String toString(){
        return "id:"+id+";"+
                "hostName:"+hostName+";"+
                "os:"+os+";"+
                "memUsed:"+memUsed+";"+
                "memTotal:"+memTotal+";"+
                "diskUsed:"+diskUsed+";"+
                "diskTotal:"+diskTotal+";"+
                "cpuPercent:"+cpuPercent+";"+
                "baseboardSerial:"+baseboardSerial+";"+
                "swapUsed:"+swapUsed+";"+
                "swapTotal:"+swapTotal+";";
    }
    public static void main(String[] s){
        SysStatusEntity e = new SysStatusEntity();
       //;memUsed:82609233920;
        // memTotal:134948577280;
        // diskUsed:22895478784;
        // diskTotal:101044172800;
        // cpuPercent:0.08382971633273628;baseboardSerial:;
        // swapUsed:0;
        // swapTotal:137204068352;
        e.setMemUsed(82609233920l);
        e.setMemTotal(134948577280l);
        //System.out.println(e.getMemUsedPercent());

        //System.out.println(82609233920l/1024/1024/1024);  //76.93

        System.out.println(e.getMemUsedGB());

    }
}
