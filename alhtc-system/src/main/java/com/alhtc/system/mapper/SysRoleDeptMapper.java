package com.alhtc.system.mapper;

import com.alhtc.system.domain.SysRoleDept;

import java.util.List;

public interface SysRoleDeptMapper {

	/**
	 * 通过角色ID删除角色和部门关联
	 *
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int deleteRoleDeptByRoleId(Long roleId);

	/**
	 * 批量新增角色部门信息
	 *
	 * @param roleDeptList 角色部门列表
	 * @return 结果
	 */
	int batchRoleDept(List<SysRoleDept> roleDeptList);

	/**
	 * 批量删除角色部门关联信息
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	int deleteRoleDept(Long[] ids);
}
