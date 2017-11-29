package io.cem.modules.cem.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-23 11:22:22
 */
public class LayerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//层级ID
	private Integer id;
	//调度策略ID
	private Integer layerTag;
	//层级名称
	private String layerName;

	/**
	 * 设置：层级ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：层级ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：调度策略ID
	 */
	public void setLayerTag(Integer layerTag) {
		this.layerTag = layerTag;
	}
	/**
	 * 获取：调度策略ID
	 */
	public Integer getLayerTag() {
		return layerTag;
	}
	/**
	 * 设置：层级名称
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	/**
	 * 获取：层级名称
	 */
	public String getLayerName() {
		return layerName;
	}
}
