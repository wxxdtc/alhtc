package com.alhtc.framework.web.service;

import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.common.enums.UserStatus;
import com.alhtc.common.exception.ServiceException;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author wangxiaoxu
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ISysUserService userService;

	private final SysPermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userService.selectUserByUserName(username);
		if (StringUtils.isNull(user)) {
			log.info("登录用户：{} 不存在.", username);
			throw new ServiceException("登录用户：" + username + " 不存在");
		} else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			log.info("登录用户：{} 已被删除.", username);
			throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", username);
			throw new ServiceException("对不起，您的账号：" + username + " 已停用");
		}
		return createLoginUser(user);
	}

	public UserDetails createLoginUser(SysUser user) {
		return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
	}
}
