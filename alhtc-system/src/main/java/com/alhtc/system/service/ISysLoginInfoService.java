package com.alhtc.system.service;

import com.alhtc.system.domain.SysLoginInfo;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author wangxiaoxu
 */
public interface ISysLoginInfoService {

	/**
	 * 新增系统登录日志
	 *
	 * @param loginInfo 访问日志对象
	 */
	void insertLoginInfo(SysLoginInfo loginInfo);
}
