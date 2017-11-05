package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 行政区域县区信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public class CountyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String countyId;
	//
	private String countyName;
	//
	private String cityId;

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
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	/**
	 * 获取：
	 */
	public String getCountyId() {
		return countyId;
	}
	/**
	 * 设置：
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	/**
	 * 获取：
	 */
	public String getCountyName() {
		return countyName;
	}
	/**
	 * 设置：
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：
	 */
	public String getCityId() {
		return cityId;
	}
}
