package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 行政区域地州市信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
public class CityEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String cityId;
	//
	private String cityName;
	//
	private String provinceId;

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
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * 设置：
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取：
	 */
	public String getProvinceId() {
		return provinceId;
	}
}
