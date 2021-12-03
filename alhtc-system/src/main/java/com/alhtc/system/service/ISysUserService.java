package com.alhtc.system.service;

import com.alhtc.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author wangxiaoxu
 */
public interface ISysUserService {

	/**
	 * 通过用户ID查询用户
	 *
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	SysUser selectUserById(Long userId);

	/**
	 * 根据条件分页查询用户列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUser> selectUserList(SysUser user);

	/**
	 * 通过用户名查询用户
	 *
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	SysUser selectUserByUserName(String userName);

	/**
	 * 修改用户基本信息
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUserProfile(SysUser user);

	/**
	 * 校验用户是否允许操作
	 *
	 * @param user 用户信息
	 */
	void checkUserAllowed(SysUser user);

	/**
	 * 修改用户状态
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUserStatus(SysUser user);

	/**
	 * 校验用户是否有数据权限
	 *
	 * @param userId 用户id
	 */
	void checkUserDataScope(Long userId);

	/**
	 * 根据条件分页查询已分配用户角色列表
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
