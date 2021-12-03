package com.alhtc.web.system;

import com.alhtc.common.annotation.Log;
import com.alhtc.common.constant.UserConstants;
import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysMenu;
import com.alhtc.common.enums.BusinessType;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alhtc.common.utils.SecurityUtils.getUserId;

/**
 * 菜单信息
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMenuController extends BaseController {

	private final ISysMenuService menuService;

	/**
	 * 获取菜单列表
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:list')")
	@GetMapping("/list")
	public R list(SysMenu menu) {
		List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
		return R.success(menus);
	}

	/**
	 * 根据菜单编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:query')")
	@GetMapping(value = "/{menuId}")
	public R getInfo(@PathVariable Long menuId) {
		return R.success(menuService.selectMenuById(menuId));
	}

	/**
	 * 获取菜单下拉树列表
	 */
	@GetMapping("/treeselect")
	public R treeselect(SysMenu menu)
	{
		List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
		return R.success(menuService.buildMenuTreeSelect(menus));
	}

	/**
	 * 新增菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:add')")
	@Log(title = "菜单管理", businessType = BusinessType.INSERT)
	@PostMapping
	public R add(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return R.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return R.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		}
		menu.setCreateBy(getUsername());
		return toR(menuService.insertMenu(menu));
	}

	/**
	 * 修改菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:edit')")
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public R edit(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return R.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return R.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		} else if (menu.getMenuId().equals(menu.getParentId())) {
			return R.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
		}
		menu.setUpdateBy(getUsername());
		return toR(menuService.updateMenu(menu));
	}

	/**
	 * 删除菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:remove')")
	@Log(title = "菜单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuId}")
	public R remove(@PathVariable("menuId") Long menuId) {
		if (menuService.hasChildByMenuId(menuId)) {
			return R.error("存在子菜单,不允许删除");
		}
		if (menuService.checkMenuExistRole(menuId)) {
			return R.error("菜单已分配,不允许删除");
		}
		return toR(menuService.deleteMenuById(menuId));
	}

	/**
	 * 加载对应角色菜单列表树
	 */
	@GetMapping(value = "/roleMenuTreeselect/{roleId}")
	public R roleMenuTreeselect(@PathVariable("roleId") Long roleId)
	{
		List<SysMenu> menus = menuService.selectMenuList(getUserId());
		R r = R.success();
		r.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
		r.put("menus", menuService.buildMenuTreeSelect(menus));
		return r;
	}
}
