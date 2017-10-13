package io.cem.modules.cem.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
 */
public class DicDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//字典数据ID
	private Integer id;
	//字典类别ID
	private Integer dicTypeId;
	//字典数据ID
	private Integer dataId;
	//字典数据名称
	private String dataName;
	//备注
	private String remark;

	/**
	 * 设置：字典数据ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：字典数据ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：字典类别ID
	 */
	public void setDicTypeId(Integer dicTypeId) {
		this.dicTypeId = dicTypeId;
	}
	/**
	 * 获取：字典类别ID
	 */
	public Integer getDicTypeId() {
		return dicTypeId;
	}
	/**
	 * 设置：字典数据ID
	 */
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	/**
	 * 获取：字典数据ID
	 */
	public Integer getDataId() {
		return dataId;
	}
	/**
	 * 设置：字典数据名称
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * 获取：字典数据名称
	 */
	public String getDataName() {
		return dataName;
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
