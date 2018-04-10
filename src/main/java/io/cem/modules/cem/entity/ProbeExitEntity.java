package io.cem.modules.cem.entity;

import java.io.Serializable;



/**
 * 端口-出口对照表
 */
public class ProbeExitEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer probeId;
	//
	private String port;
	//
	private String exit;
	//
	private Integer status;

	private String probeName;

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
	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}
	/**
	 * 获取：
	 */
	public Integer getProbeId() {
		return probeId;
	}
	/**
	 * 设置：
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * 获取：
	 */
	public String getPort() {
		return port;
	}
	/**
	 * 设置：
	 */
	public void setExit(String exit) {
		this.exit = exit;
	}
	/**
	 * 获取：
	 */
	public String getExit() {
		return exit;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
}
