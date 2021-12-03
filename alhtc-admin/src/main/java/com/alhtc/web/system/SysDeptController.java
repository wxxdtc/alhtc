package com.alhtc.web.system;

import com.alhtc.common.annotation.Log;
import com.alhtc.common.constant.UserConstants;
import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysDept;
import com.alhtc.common.enums.BusinessType;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDeptController extends BaseController {

	private final ISysDeptService deptService;

	/**
	 * 获取部门列表
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list")
	public R list(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return R.success(depts);
	}

	/**
	 * 获取部门下拉树列表
	 */
	@GetMapping("/treeselect")
	public R treeselect(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return R.success(deptService.buildDeptTreeSelect(depts));
	}

	/**
	 * 加载对应角色部门列表树
	 */
	@GetMapping(value = "/roleDeptTreeselect/{roleId}")
	public R roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		R r = R.success();
		r.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
		r.put("depts", deptService.buildDeptTreeSelect(depts));
		return r;
	}

	/**
	 * 根据部门编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:query')")
	@GetMapping(value = "/{deptId}")
	public R getInfo(@PathVariable Long deptId) {
		deptService.checkDeptDataScope(deptId);
		return R.success(deptService.selectDeptById(deptId));
	}

	/**
	 * 查询部门列表（排除节点）
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list/exclude/{deptId}")
	public R excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		depts.removeIf(d -> d.getDeptId().intValue() == deptId
				|| ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
		return R.success(depts);
	}

	/**
	 * 新增部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:add')")
	@Log(title = "部门管理", businessType = BusinessType.INSERT)
	@PostMapping
	public R add(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return R.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(getUsername());
		return toR(deptService.insertDept(dept));
	}

	/**
	 * 修改部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:edit')")
	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public R edit(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return R.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		} else if (dept.getParentId().equals(dept.getDeptId())) {
			return R.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
		} else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
			return R.error("该部门包含未停用的子部门！");
		}
		dept.setUpdateBy(getUsername());
		return toR(deptService.updateDept(dept));
	}

	/**
	 * 删除部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:remove')")
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	public R remove(@PathVariable Long deptId) {
		if (deptService.hasChildByDeptId(deptId)) {
			return R.error("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return R.error("部门存在用户,不允许删除");
		}
		return toR(deptService.deleteDeptById(deptId));
	}

}
