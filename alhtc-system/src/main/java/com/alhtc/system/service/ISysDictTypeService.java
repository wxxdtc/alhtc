package com.alhtc.system.service;

import com.alhtc.common.core.domain.entity.SysDictData;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author wangxiaoxu
 */
public interface ISysDictTypeService {

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	public List<SysDictData> selectDictDataByType(String dictType);
}
