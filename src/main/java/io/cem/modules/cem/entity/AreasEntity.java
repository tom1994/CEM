package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 行政区域县区信息表
 */
public class AreasEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer areaid;
	//
	private String area;
	//
	private Integer cityid;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	/**
	 * 获取：
	 */
	public Integer getAreaid() {
		return areaid;
	}
	/**
	 * 设置：
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取：
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 设置：
	 */
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	/**
	 * 获取：
	 */
	public Integer getCityid() {
		return cityid;
	}
}
