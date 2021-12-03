package com.alhtc.common.exception.user;

import static com.alhtc.common.enums.SystemMessage.USER_CAPTCHA_ERROR;

/**
 * 验证码错误异常类
 *
 * @author wangxiaoxu
 */
public class CaptchaException extends UserException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super(USER_CAPTCHA_ERROR.getCode(), null, USER_CAPTCHA_ERROR.getInfo());
	}
}
