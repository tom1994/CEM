package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
public class TargetGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//测试目标组ID
	private Integer id;
	//名称
	private String name;
	//备注
	private String remark;
	//创建时间
	private Date createTime;

	/**
	 * 设置：测试目标组ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：测试目标组ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
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
	public Date getCreateTime() {
		return createTime;
	}
}
