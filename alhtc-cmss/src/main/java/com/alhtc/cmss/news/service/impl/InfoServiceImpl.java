package com.alhtc.cmss.news.service.impl;

import com.alhtc.cmss.news.domain.model.VillageInformation;
import com.alhtc.cmss.news.mapper.InfoMapper;
import com.alhtc.cmss.news.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 信息 服务层实现
 *
 * @author lei.si
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfoServiceImpl implements InfoService {

	private final InfoMapper infoMapper;

	/**
	 * 查询信息
	 *
	 * @param informationId 信息ID
	 * @return 信息
	 */
	@Override
	public VillageInformation selectInformationById(Long informationId) {
		return infoMapper.selectInformationById(informationId);
	}

	/**
	 * 查询信息
	 *
	 * @param information 信息
	 * @return 信息集合
	 */
	@Override
	public List<VillageInformation> selectInformationList(VillageInformation information) {
		return infoMapper.selectInformationList(information);
	}

	/**
	 * 新增信息
	 *
	 * @param information 信息
	 * @return 结果
	 */
	@Override
	public int insertInformation(VillageInformation information) {
		return infoMapper.insertInformation(information);
	}

	/**
	 * 修改信息
	 *
	 * @param information 信息
	 * @return 结果
	 */
	@Override
	public int updateInformation(VillageInformation information) {
		return infoMapper.updateInformation(information);
	}

	/**
	 * 删除信息对象
	 *
	 * @param informationId 信息ID
	 * @return 结果
	 */
	@Override
	public int deleteInformationById(Long informationId) {
		return infoMapper.deleteInformationById(informationId);
	}

	/**
	 * 批量删除信息
	 *
	 * @param informationIds 需要删除的信息ID
	 * @return 结果
	 */
	@Override
	public int deleteInformationByIds(Long[] informationIds) {
		return infoMapper.deleteInformationByIds(informationIds);
	}
}
