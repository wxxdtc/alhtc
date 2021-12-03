package com.alhtc.system.service.impl;

import com.alhtc.common.annotation.DataScope;
import com.alhtc.common.core.domain.entity.SysUser;
import com.alhtc.common.exception.ServiceException;
import com.alhtc.common.utils.SecurityUtils;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.common.utils.spriing.SpringUtils;
import com.alhtc.system.mapper.SysUserMapper;
import com.alhtc.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements ISysUserService {

	private final SysUserMapper userMapper;

	/**
	 * 通过用户ID查询用户
	 *
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserById(Long userId) {
		return userMapper.selectUserById(userId);
	}

	/**
	 * 根据条件分页查询用户列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectUserList(SysUser user) {
		return userMapper.selectUserList(user);
	}


	@Override
	public SysUser selectUserByUserName(String userName) {
		return userMapper.selectUserByUserName(userName);
	}

	/**
	 * 修改用户基本信息
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserProfile(SysUser user) {
		return userMapper.updateUser(user);
	}

	/**
	 * 校验用户是否允许操作
	 *
	 * @param user 用户信息
	 */
	@Override
	public void checkUserAllowed(SysUser user) {
		if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
			throw new ServiceException("不允许操作超级管理员用户");
		}
	}

	/**
	 * 修改用户状态
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserStatus(SysUser user) {
		return userMapper.updateUser(user);
	}

	/**
	 * 校验用户是否有数据权限
	 *
	 * @param userId 用户id
	 */
	@Override
	public void checkUserDataScope(Long userId) {
		if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
			SysUser user = new SysUser();
			user.setUserId(userId);
			List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
			if (StringUtils.isEmpty(users)) {
				throw new ServiceException("没有权限访问用户数据！");
			}
		}
	}

	/**
	 * 根据条件分页查询已分配用户角色列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectAllocatedList(SysUser user) {
		return userMapper.selectAllocatedList(user);
	}

	/**
	 * 根据条件分页查询未分配用户角色列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectUnallocatedList(SysUser user) {
		return userMapper.selectUnallocatedList(user);
	}
}
