package io.cem.modules.cem.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public class DicTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//字典类别ID
	private Integer id;
	//字典类别名称
	private String dicName;
	//备注
	private String remark;

	/**
	 * 设置：字典类别ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：字典类别ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：字典类别名称
	 */
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	/**
	 * 获取：字典类别名称
	 */
	public String getDicName() {
		return dicName;
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
}
