package com.alhtc.system.mapper;

import com.alhtc.common.core.domain.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {

	/**
	 * 根据条件分页查询角色数据
	 *
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	List<SysRole> selectRoleList(SysRole role);

	/**
	 * 根据用户ID查询角色
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	List<SysRole> selectRolePermissionByUserId(Long userId);

	/**
	 * 查询所有角色
	 *
	 * @return 角色列表
	 */
	List<SysRole> selectRoleAll();

	/**
	 * 根据用户ID获取角色选择框列表
	 *
	 * @param userId 用户ID
	 * @return 选中角色ID列表
	 */
	List<Long> selectRoleListByUserId(Long userId);

	/**
	 * 修改角色信息
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	int updateRole(SysRole role);

	/**
	 * 校验角色名称是否唯一
	 *
	 * @param roleName 角色名称
	 * @return 角色信息
	 */
	SysRole checkRoleNameUnique(String roleName);

	/**
	 * 校验角色权限是否唯一
	 *
	 * @param roleKey 角色权限
	 * @return 角色信息
	 */
	SysRole checkRoleKeyUnique(String roleKey);

	/**
	 * 新增角色信息
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	int insertRole(SysRole role);

	/**
	 * 通过角色ID查询角色
	 *
	 * @param roleId 角色ID
	 * @return 角色对象信息
	 */
	SysRole selectRoleById(Long roleId);

	/**
	 * 批量删除角色信息
	 *
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	int deleteRoleByIds(Long[] roleIds);

}
