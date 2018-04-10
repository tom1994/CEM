package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 */
public class SchedulePolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//调度策略ID
	private Integer id;
	//名称
	private String spName;
	//JSON字符串
	private String scheduler;
	//备注
	private String remark;
	//开始日期
	private Date startDate;
	//结束日期
	private Date endDate;
	//创建时间
	private Date createTime;

	/**
	 * 设置：调度策略ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：调度策略ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setSpName(String spName) {
		this.spName = spName;
	}
	/**
	 * 获取：名称
	 */
	public String getSpName() {
		return spName;
	}
	/**
	 * 设置：
	 */
	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}
	/**
	 * 获取：
	 */
	public String getScheduler() {
		return scheduler;
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
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 设置：结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取：结束日期
	 */
	public Date getEndDate() {
		return endDate;
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

}
