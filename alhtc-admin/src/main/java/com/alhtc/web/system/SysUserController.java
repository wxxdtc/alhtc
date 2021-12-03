package com.alhtc.web.system;

import com.alhtc.common.annotation.Log;
import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysRole;
import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.core.page.TableDataInfo;
import com.alhtc.common.enums.BusinessType;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.service.ISysRoleService;
import com.alhtc.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserController extends BaseController {

	private final ISysUserService userService;

	private final ISysRoleService roleService;

	/**
	 * 获取用户列表
	 */
	@PreAuthorize("@ss.hasPermi('system:user:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectUserList(user);
		return getDataTable(list);
	}

	/**
	 * 根据用户编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:user:query')")
	@GetMapping(value = { "/", "/{userId}" })
	public R getInfo(@PathVariable(value = "userId", required = false) Long userId)
	{
		userService.checkUserDataScope(userId);
		R r = R.success();
		List<SysRole> roles = roleService.selectRoleAll();
		r.put("roles", SysUser.isAdmin(userId) ?
				roles : roles.stream().filter(role -> !role.isAdmin()).collect(Collectors.toList()));
		if (StringUtils.isNotNull(userId))
		{
			r.put(R.DATA_TAG, userService.selectUserById(userId));
			r.put("roleIds", roleService.selectRoleListByUserId(userId));
		}
		return r;
	}

	/**
	 * 根据用户编号获取授权角色
	 */
	@PreAuthorize("@ss.hasPermi('system:user:query')")
	@GetMapping("/authRole/{userId}")
	public R authRole(@PathVariable("userId") Long userId) {
		R r = R.success();
		SysUser user = userService.selectUserById(userId);
		List<SysRole> roles = roleService.selectRolesByUserId(userId);
		r.put("user", user);
		r.put("roles", SysUser.isAdmin(userId) ?
				roles : roles.stream().filter(role -> !role.isAdmin()).collect(Collectors.toList()));
		return r;
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public R changeStatus(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setUpdateBy(getUsername());
		return toR(userService.updateUserStatus(user));
	}

}
