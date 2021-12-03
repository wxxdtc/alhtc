package com.alhtc.system.service.impl;

import com.alhtc.system.domain.SysLoginInfo;
import com.alhtc.system.mapper.SysLoginInfoMapper;
import com.alhtc.system.service.ISysLoginInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

	private final SysLoginInfoMapper loginInfoMapper;

	@Override
	public void insertLoginInfo(SysLoginInfo loginInfo) {
		loginInfoMapper.insertLoginInfo(loginInfo);
	}
}
