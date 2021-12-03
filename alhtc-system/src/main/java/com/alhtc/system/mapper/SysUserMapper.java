package com.alhtc.system.mapper;

import com.alhtc.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author wangxiaoxu
 */
public interface SysUserMapper {

	/**
	 * 通过用户ID查询用户
	 *
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	public SysUser selectUserById(Long userId);

	/**
	 * 根据条件分页查询用户列表
	 *
	 * @param sysUser 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUser> selectUserList(SysUser sysUser);

	/**
	 * 通过用户名查询用户
	 *
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	SysUser selectUserByUserName(String userName);

	/**
	 * 修改用户信息
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUser(SysUser user);

	/**
	 * 根据条件分页查询未已配用户角色列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUser> selectAllocatedList(SysUser user);

	/**
	 * 根据条件分页查询未分配用户角色列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUser> selectUnallocatedList(SysUser user);
}
