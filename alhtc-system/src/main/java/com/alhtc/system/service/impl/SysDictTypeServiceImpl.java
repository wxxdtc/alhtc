package com.alhtc.system.service.impl;

import com.alhtc.common.core.domain.entity.SysDictData;
import com.alhtc.common.utils.DictUtils;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.mapper.SysDictDataMapper;
import com.alhtc.system.mapper.SysDictTypeMapper;
import com.alhtc.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author wangxiaoxu
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDictTypeServiceImpl implements ISysDictTypeService {

	private final SysDictTypeMapper dictTypeMapper;

	private final SysDictDataMapper dictDataMapper;

	/**
	 * 根据字典类型查询字典数据
	 *
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	@Override
	public List<SysDictData> selectDictDataByType(String dictType)
	{
		List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
		if (StringUtils.isNotEmpty(dictDatas))
		{
			return dictDatas;
		}
		dictDatas = dictDataMapper.selectDictDataByType(dictType);
		if (StringUtils.isNotEmpty(dictDatas))
		{
			DictUtils.setDictCache(dictType, dictDatas);
			return dictDatas;
		}
		return null;
	}
}
