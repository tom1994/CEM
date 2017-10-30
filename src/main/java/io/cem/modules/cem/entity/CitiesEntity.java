package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 行政区域地州市信息表
 */
public class CitiesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer cityid;
	//
	private String city;
	//
	private Integer provinceid;

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
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	/**
	 * 获取：
	 */
	public Integer getCityid() {
		return cityid;
	}
	/**
	 * 设置：
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：
	 */
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * 获取：
	 */
	public Integer getProvinceid() {
		return provinceid;
	}
}
