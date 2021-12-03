package com.alhtc.framework.web.service;

import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.common.utils.SecurityUtils;
import com.alhtc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * 自定义权限实现，ss取自SpringSecurity首字母
 *
 * @author wangxiaoxu
 */
@Slf4j
@Service("ss")
public class PermissionService {

	/**
	 * 所有权限标识
	 */
	private static final String ALL_PERMISSION = "*:*:*";

	/**
	 * 管理员角色权限标识
	 */
	private static final String SUPER_ADMIN = "admin";

	private static final String ROLE_DELIMETER = ",";

	private static final String PERMISSION_DELIMETER = ",";

	/**
	 * 验证用户是否具备某权限
	 *
	 * @param permission 权限字符串
	 * @return 用户是否具备某权限
	 */
	public boolean hasPermi(String permission) {
		if (StringUtils.isEmpty(permission)) {
			return false;
		}
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		log.info("request need permission:{}", permission);
		log.info("current user permissions:{}", loginUser.getPermissions());
		return hasPermissions(loginUser.getPermissions(), permission);
	}

	/**
	 * 判断是否包含权限
	 *
	 * @param permissions 权限列表
	 * @param permission  权限字符串
	 * @return 用户是否具备某权限
	 */
	private boolean hasPermissions(Set<String> permissions, String permission) {
		return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
	}

}
