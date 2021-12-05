package com.alhtc.cmss.news.service;

import com.alhtc.cmss.news.domain.model.VillageInformation;

import java.util.List;

/**
 * 信息 服务层
 *
 * @author lei.si
 */
public interface InfoService {
	/**
	 * 查询信息
	 *
	 * @param informationId 信息ID
	 * @return 信息
	 */
	VillageInformation selectInformationById(Long informationId);

	/**
	 * 查询信息列表
	 *
	 * @param information 信息
	 * @return 信息集合
	 */
	List<VillageInformation> selectInformationList(VillageInformation information);

	/**
	 * 新增信息
	 *
	 * @param information 信息
	 * @return 结果
	 */
	int insertInformation(VillageInformation information);

	/**
	 * 修改信息
	 *
	 * @param information 信息
	 * @return 结果
	 */
	int updateInformation(VillageInformation information);

	/**
	 * 删除信息
	 *
	 * @param informationId 信息ID
	 * @return 结果
	 */
	int deleteInformationById(Long informationId);

	/**
	 * 批量删除信息
	 *
	 * @param informationIds 需要删除的信息ID
	 * @return 结果
	 */
	int deleteInformationByIds(Long[] informationIds);
}
