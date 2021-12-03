package com.alhtc.system.mapper;

import com.alhtc.system.domain.SysConfig;

import java.util.List;

/**
 * 参数配置 数据层
 *
 * @author wangxiaoxu
 */
public interface SysConfigMapper {

	/**
	 * 查询参数配置信息
	 *
	 * @param config 参数配置信息
	 * @return 参数配置信息
	 */
	public SysConfig selectConfig(SysConfig config);

	/**
	 * 查询参数配置列表
	 *
	 * @param config 参数配置信息
	 * @return 参数配置集合
	 */
	List<SysConfig> selectConfigList(SysConfig config);
}
