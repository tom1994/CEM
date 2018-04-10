package io.cem.modules.cem.entity;

import java.io.Serializable;


/**
 *
 */
public class ErrorCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer code;
	//
	private String message;

	/**
	 * 设置：
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取：
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置：
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取：
	 */
	public String getMessage() {
		return message;
	}
}
