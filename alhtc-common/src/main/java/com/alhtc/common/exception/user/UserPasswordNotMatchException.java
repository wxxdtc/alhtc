package com.alhtc.common.exception.user;

import static com.alhtc.common.enums.SystemMessage.USER_PASSWORD_NOT_MATCH;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author wangxiaoxu
 */
public class UserPasswordNotMatchException extends UserException {
	private static final long serialVersionUID = 1L;

	public UserPasswordNotMatchException() {
		super(USER_PASSWORD_NOT_MATCH.getCode(), null, USER_PASSWORD_NOT_MATCH.getInfo());
	}
}
