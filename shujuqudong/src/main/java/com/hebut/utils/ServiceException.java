package com.hebut.utils;

/**
 * 主动抛出的异常
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 8109469326798389194L;

	private Integer errorCode;

	public ServiceException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
