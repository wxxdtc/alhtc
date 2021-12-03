package com.alhtc.framework.web.service;

import com.alhtc.common.constant.Constants;
import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.common.core.redis.RedisCache;
import com.alhtc.common.exception.ServiceException;
import com.alhtc.common.exception.user.CaptchaException;
import com.alhtc.common.exception.user.CaptchaExpireException;
import com.alhtc.common.exception.user.UserPasswordNotMatchException;
import com.alhtc.common.utils.DateUtils;
import com.alhtc.common.utils.ServletUtils;
import com.alhtc.common.utils.ip.IpUtils;
import com.alhtc.framework.manager.AsyncManager;
import com.alhtc.framework.manager.factory.AsyncFactory;
import com.alhtc.system.service.ISysConfigService;
import com.alhtc.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.alhtc.common.enums.SystemMessage.*;

/**
 * 登录校验方法
 *
 * @author wangxiaoxu
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginService {

	private final TokenService tokenService;

	private final RedisCache redisCache;

	private final ISysConfigService configService;

	private final AuthenticationManager authenticationManager;

	private final ISysUserService userService;

	public String login(String username, String password, String code, String uuid) {
		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 验证码开关
		if (captchaOnOff) {
			validateCaptcha(username, code, uuid);
		}
		// 用户验证
		Authentication authentication;
		try {
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, USER_PASSWORD_NOT_MATCH.getInfo()));
				throw new UserPasswordNotMatchException();
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, e.getMessage()));
				throw new ServiceException(e.getMessage());
			}
		}
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_SUCCESS, LOGIN_SUCCESS.getInfo()));
		recordLoginInfo(loginUser.getUserId());
		// 生成token
		return tokenService.createToken(loginUser);
	}

	/**
	 * 校验验证码
	 *
	 * @param username 用户名
	 * @param code     验证码
	 * @param uuid     唯一标识
	 */
	public void validateCaptcha(String username, String code, String uuid) {
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		String captcha = redisCache.getCacheObject(verifyKey);
		redisCache.deleteObject(verifyKey);
		if (captcha == null) {
			AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, USER_CAPTCHA_EXPIRE.getInfo()));
			throw new CaptchaExpireException();
		}
		if (!code.equalsIgnoreCase(captcha)) {
			AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, USER_CAPTCHA_ERROR.getInfo()));
			throw new CaptchaException();
		}

	}

	/**
	 * 记录登录信息
	 *
	 * @param userId 用户ID
	 */
	public void recordLoginInfo(Long userId) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
		sysUser.setLoginDate(DateUtils.getNowDate());
		userService.updateUserProfile(sysUser);
	}
}
