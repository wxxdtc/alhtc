package com.alhtc.framework.web.exception;

import com.alhtc.common.constant.HttpStatus;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.exception.ServiceException;
import com.alhtc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author wangxiaoxu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 权限校验异常
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public R handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
		return R.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
	}

	/**
	 * 请求方式不支持
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
														  HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
		return R.error(e.getMessage());
	}

	/**
	 * 业务异常
	 */
	@ExceptionHandler(ServiceException.class)
	public R handleServiceException(ServiceException e, HttpServletRequest request)
	{
		log.error(e.getMessage(), e);
		Integer code = e.getCode();
		return StringUtils.isNotNull(code) ? R.error(code, e.getMessage()) : R.error(e.getMessage());
	}

	/**
	 * 拦截未知的运行时异常
	 */
	@ExceptionHandler(RuntimeException.class)
	public R handleRuntimeException(RuntimeException e, HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		log.error("请求地址'{}',发生未知异常.", requestURI, e);
		return R.error(e.getMessage());
	}

	/**
	 * 系统异常
	 */
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e, HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		log.error("请求地址'{}',发生系统异常.", requestURI, e);
		return R.error(e.getMessage());
	}

	/**
	 * 自定义验证异常
	 */
	@ExceptionHandler(BindException.class)
	public R handleBindException(BindException e)
	{
		log.error(e.getMessage(), e);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return R.error(message);
	}

	/**
	 * 自定义验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
	{
		log.error(e.getMessage(), e);
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		return R.error(message);
	}



}
