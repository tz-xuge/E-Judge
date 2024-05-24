package com.hebut.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Set;

/**
 * 统一拦截异常
 */
@RestControllerAdvice
public class GlobalDefultExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ErrorMessage ServiceException(ServiceException e) {
		e.printStackTrace();
		return new ErrorMessage(e.getErrorCode(), e.getMessage());
	}

	// 空值异常
	@ExceptionHandler(NullPointerException.class)
	public ErrorMessage NullPointerException(NullPointerException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "空值异常");
	}

	// 字符串转换为数字异常
	@ExceptionHandler(NumberFormatException.class)
	public ErrorMessage NumberFormatException(NumberFormatException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "字符串转换为数字异常");
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ErrorMessage IndexOutOfBoundsException(IndexOutOfBoundsException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "数组越界异常");
	}

	// 方法传递参数错误
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorMessage IllegalArgumentException(IllegalArgumentException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "方法传递参数错误");
	}

	// SQL语句错误
	@ExceptionHandler(SQLException.class)
	public ErrorMessage SQLException(SQLException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "SQL语句错误");
	}

	// 唯一索引约束异常
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorMessage DataIntegrityViolationException(DataIntegrityViolationException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "无法添加、删除或更新数据，请检查数据是否存在或被引用");
	}

	// 上传的文件不存在异常
	@ExceptionHandler(MultipartException.class)
	public ErrorMessage FileUploadException(MultipartException e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "上传的文件不存在");
	}


	// 参数格式异常
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorMessage ConstraintViolationException(ConstraintViolationException ex) {
		ex.printStackTrace();
		StringBuilder errorMsg = new StringBuilder();
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		for (ConstraintViolation<?> item : violations) {
			errorMsg.append(item.getMessage()).append(",");
		}
		errorMsg.delete(errorMsg.length() - 1, errorMsg.length());

		return new ErrorMessage(-1, errorMsg.toString());
	}

	// 效验json格式异常
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorMessage MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ex.printStackTrace();
		StringBuilder errorMsg = new StringBuilder();
		BindingResult re = ex.getBindingResult();
		for (ObjectError error : re.getAllErrors()) {
			errorMsg.append(error.getDefaultMessage()).append(",");
		}
		errorMsg.delete(errorMsg.length() - 1, errorMsg.length());

		return new ErrorMessage(-1, errorMsg.toString());
	}

	// 效验表单格式异常
	@ExceptionHandler(BindException.class)
	public ErrorMessage BindException(BindException ex) {
		ex.printStackTrace();
		BindingResult result = ex.getBindingResult();
		StringBuilder errorMsg = new StringBuilder();
		for (ObjectError error : result.getAllErrors()) {
			errorMsg.append(error.getDefaultMessage()).append(",");
		}
		errorMsg.delete(errorMsg.length() - 1, errorMsg.length());

		return new ErrorMessage(-1, errorMsg.toString());
	}

	// 最终异常
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorMessage HandleException(Exception e) {
		e.printStackTrace();
		return new ErrorMessage(-1, "后台未知异常，请重试");
	}
}
