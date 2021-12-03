package com.alhtc.system.mapper;

import com.alhtc.common.core.domain.entity.SysDictData;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author wangxiaoxu
 */
public interface SysDictDataMapper {

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	List<SysDictData> selectDictDataByType(String dictType);
}
