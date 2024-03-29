package com.alhtc.system.mapper;

import com.alhtc.system.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author wangxiaoxu
 */
public interface SysUserRoleMapper {

	/**
	 * 批量新增用户角色信息
	 *
	 * @param userRoleList 用户角色列表
	 * @return 结果
	 */
	int batchUserRole(List<SysUserRole> userRoleList);

	/**
	 * 删除用户和角色关联信息
	 *
	 * @param userRole 用户和角色关联信息
	 * @return 结果
	 */
	public int deleteUserRoleInfo(SysUserRole userRole);

	/**
	 * 批量取消授权用户角色
	 *
	 * @param roleId  角色ID
	 * @param userIds 需要删除的用户数据ID
	 * @return 结果
	 */
	int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);

	/**
	 * 通过角色ID查询角色使用数量
	 *
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int countUserRoleByRoleId(Long roleId);
}
