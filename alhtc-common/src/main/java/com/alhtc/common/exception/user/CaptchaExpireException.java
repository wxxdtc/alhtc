package com.alhtc.common.exception.user;

import static com.alhtc.common.enums.SystemMessage.USER_CAPTCHA_EXPIRE;

/**
 * 验证码失效异常类
 *
 * @author ruoyi
 */
public class CaptchaExpireException extends UserException {
	private static final long serialVersionUID = 1L;

	public CaptchaExpireException() {
		super(USER_CAPTCHA_EXPIRE.getCode(), null, USER_CAPTCHA_EXPIRE.getInfo());
	}
}
