package com.hebut.utils;

import java.io.Serializable;

/**
 * 异常信息返回类
 */
public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = 8065583911104112360L;
	/** 返回的结果码【0：成功；-1：失败】 */
	private Integer status;
	/** 请求信息 */
	private String msg;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ErrorMessage(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public ErrorMessage() {
		super();
	}
}
