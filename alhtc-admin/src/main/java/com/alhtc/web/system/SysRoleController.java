package com.alhtc.web.system;

import com.alhtc.common.annotation.Log;
import com.alhtc.common.constant.UserConstants;
import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysRole;
import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.common.core.page.TableDataInfo;
import com.alhtc.common.enums.BusinessType;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.framework.web.service.SysPermissionService;
import com.alhtc.framework.web.service.TokenService;
import com.alhtc.system.domain.SysUserRole;
import com.alhtc.system.service.ISysRoleService;
import com.alhtc.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alhtc.common.utils.SecurityUtils.getLoginUser;

/**
 * 角色信息
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRoleController extends BaseController {


	private final ISysRoleService roleService;

	private final SysPermissionService permissionService;

	private final ISysUserService userService;

	private final TokenService tokenService;

	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysRole role) {
		startPage();
		List<SysRole> list = roleService.selectRoleList(role);
		return getDataTable(list);
	}

	/**
	 * 根据角色编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:role:query')")
	@GetMapping(value = "/{roleId}")
	public R getInfo(@PathVariable Long roleId) {
		roleService.checkRoleDataScope(roleId);
		return R.success(roleService.selectRoleById(roleId));
	}

	/**
	 * 新增角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:add')")
	@Log(title = "角色管理", businessType = BusinessType.INSERT)
	@PostMapping
	public R add(@Validated @RequestBody SysRole role) {
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return R.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return R.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setCreateBy(getUsername());
		return toR(roleService.insertRole(role));
	}

	/**
	 * 修改保存角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public R edit(@Validated @RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return R.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return R.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setUpdateBy(getUsername());

		if (roleService.updateRole(role) > 0) {
			// 更新缓存用户权限
			LoginUser loginUser = getLoginUser();
			if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
				loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
				loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
				tokenService.setLoginUser(loginUser);
			}
			return R.success();
		}
		return R.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public R changeStatus(@RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		role.setUpdateBy(getUsername());
		return toR(roleService.updateRoleStatus(role));
	}

	/**
	 * 修改保存数据权限
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/dataScope")
	public R dataScope(@RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		return toR(roleService.authDataScope(role));
	}

	/**
	 * 查询已分配用户角色列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/authUser/allocatedList")
	public TableDataInfo allocatedList(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectAllocatedList(user);
		return getDataTable(list);
	}

	/**
	 * 查询未分配用户角色列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/authUser/unallocatedList")
	public TableDataInfo unallocatedList(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectUnallocatedList(user);
		return getDataTable(list);
	}

	/**
	 * 批量选择用户授权
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/selectAll")
	public R selectAuthUserAll(Long roleId, Long[] userIds) {
		return toR(roleService.insertAuthUsers(roleId, userIds));
	}

	/**
	 * 取消授权用户
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancel")
	public R cancelAuthUser(@RequestBody SysUserRole userRole) {
		return toR(roleService.deleteAuthUser(userRole));
	}

	/**
	 * 批量取消授权用户
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancelAll")
	public R cancelAuthUserAll(Long roleId, Long[] userIds) {
		return toR(roleService.deleteAuthUsers(roleId, userIds));
	}

	/**
	 * 删除角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:remove')")
	@Log(title = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
	public R remove(@PathVariable Long[] roleIds) {
		return toR(roleService.deleteRoleByIds(roleIds));
	}


}
