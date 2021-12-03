package com.alhtc.common.enums;

public enum SystemMessage {

	USER_CAPTCHA_ERROR("0", "验证码错误"),
	USER_CAPTCHA_EXPIRE("1", "验证码已失效"),
	LOGIN_SUCCESS("3", "登录成功"),
	USER_PASSWORD_NOT_MATCH("4", "用户名密码不匹配");

	private final String code;
	private final String info;

	SystemMessage(String code, String info)
	{
		this.code = code;
		this.info = info;
	}

	public String getCode()
	{
		return code;
	}

	public String getInfo()
	{
		return info;
	}
}
