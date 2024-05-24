package com.hebut.utils;

import java.io.Serializable;

/**
 * 统一返回值处理
 */
public class ResultUtil implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 返回的结果码【0：成功；-1：失败】 */
	private Integer status;
	/** 请求信息 */
	private String msg;
	/** 返回的结果数据 */
	private Object object;

	/**
	 * 自定义结果对象
	 *
	 * @param status 结果码
	 * @param msg    请求信息
	 * @param object 返回的对象
	 * @return ResultVo
	 */
	public static ResultUtil success(Integer status, String msg, Object object) {
		ResultUtil resultVo = new ResultUtil(status, msg, object);
		return resultVo;
	}

	/**
	 * 成功时调用
	 *
	 * @param msg    请求信息
	 * @param object 返回的对象
	 * @return ResultVo
	 */
	public static ResultUtil ok(String msg, Object object) {
		ResultUtil resultVo = null;
		if (object != null) {
			if (object instanceof Boolean) {
				if ((Boolean)object == false) {
					resultVo = new ResultUtil("操作失败，请重试", object);
					resultVo.status = -1;
					return resultVo;
				}
			}

			resultVo = new ResultUtil(msg, object);
			resultVo.status = 0;
		} else {
			resultVo = new ResultUtil("查询结果为空，请重试", object);
			resultVo.status = -1;
			return resultVo;
		}

		return resultVo;
	}

	/**
	 * 失败时调用
	 *
	 * @param msg 请求信息
	 * @return ResultVo
	 */
	public static ResultUtil err(String msg) {
		ResultUtil resultVo = new ResultUtil(msg);
		resultVo.status = -1;
		return resultVo;
	}

	public ResultUtil(String msg) {
		this.msg = msg;
	}

	public ResultUtil(String msg, Object object) {
		this.msg = msg;
		this.object = object;
	}

	public ResultUtil(Integer status, String msg, Object object) {
		this.status = status;
		this.msg = msg;
		this.object = object;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResultVo [status=" + status + ", msg=" + msg + ", object=" + object + "]";
	}

	public ResultUtil() {
		super();
	}

}
