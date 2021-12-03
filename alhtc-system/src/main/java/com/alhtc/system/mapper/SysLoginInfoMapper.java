package com.alhtc.system.mapper;

import com.alhtc.system.domain.SysLoginInfo;

public interface SysLoginInfoMapper {

	/**
	 * 新增系统登录日志
	 *
	 * @param loginInfo 访问日志对象
	 */
	void insertLoginInfo(SysLoginInfo loginInfo);
}
