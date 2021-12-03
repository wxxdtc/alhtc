package com.alhtc.web.system;

import com.alhtc.common.constant.Constants;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysMenu;
import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.core.domain.model.LoginBody;
import com.alhtc.common.utils.SecurityUtils;
import com.alhtc.framework.web.service.SysLoginService;
import com.alhtc.framework.web.service.SysPermissionService;
import com.alhtc.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author wangxiaoxu
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginController {

	private final SysLoginService loginService;

	private final ISysMenuService menuService;

	private final SysPermissionService permissionService;

	/**
	 * 登录方法
	 *
	 * @param loginBody 登录信息
	 * @return 结果
	 */
	@PostMapping("/login")
	public R login(@RequestBody LoginBody loginBody) {
		R r = R.success();
		// 生成token
		String token = loginService.login(
				loginBody.getUsername(),
				loginBody.getPassword(),
				loginBody.getCode(),
				loginBody.getUuid());
		r.put(Constants.TOKEN, token);
		return r;
	}

	/**
	 * 获取用户信息
	 */
	@GetMapping("/getInfo")
	public R getInfo() {
		SysUser user = SecurityUtils.getLoginUser().getUser();
		R r = R.success();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(user);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(user);
		r.put("user", user);
		r.put("roles", roles);
		r.put("permissions", permissions);
		return r;
	}

	/**
	 * 获取路由信息
	 *
	 * @return 路由信息
	 */
	@GetMapping("getRouters")
	public R getRouters()
	{
		Long userId = SecurityUtils.getUserId();
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
		return R.success(menuService.buildMenus(menus));
	}

}
