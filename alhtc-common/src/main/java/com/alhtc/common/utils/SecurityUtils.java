package com.alhtc.common.utils;

import com.alhtc.common.constant.HttpStatus;
import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全服务工具类
 *
 * @author wangxiaoxu
 */
public class SecurityUtils {

	/**
	 * 用户ID
	 **/
	public static Long getUserId() {
		try {
			return getLoginUser().getUserId();
		} catch (Exception e) {
			throw new ServiceException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 是否为管理员
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
