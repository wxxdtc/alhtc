package com.alhtc.cmss.news.service.impl;

import com.alhtc.cmss.news.domain.model.SysNotice;
import com.alhtc.cmss.news.mapper.NewsMapper;
import com.alhtc.cmss.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author wangxiaoxu
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewsServiceImpl implements NewsService {

	private final NewsMapper newsMapper;

	/**
	 * 查询公告信息
	 *
	 * @param noticeId 公告ID
	 * @return 公告信息
	 */
	@Override
	public SysNotice selectNoticeById(Long noticeId) {
		return newsMapper.selectNoticeById(noticeId);
	}

	/**
	 * 查询公告列表
	 *
	 * @param notice 公告信息
	 * @return 公告集合
	 */
	@Override
	public List<SysNotice> selectNoticeList(SysNotice notice) {
		return newsMapper.selectNoticeList(notice);
	}

	/**
	 * 新增公告
	 *
	 * @param notice 公告信息
	 * @return 结果
	 */
	@Override
	public int insertNotice(SysNotice notice) {
		return newsMapper.insertNotice(notice);
	}

	/**
	 * 修改公告
	 *
	 * @param notice 公告信息
	 * @return 结果
	 */
	@Override
	public int updateNotice(SysNotice notice) {
		return newsMapper.updateNotice(notice);
	}

	/**
	 * 删除公告对象
	 *
	 * @param noticeId 公告ID
	 * @return 结果
	 */
	@Override
	public int deleteNoticeById(Long noticeId) {
		return newsMapper.deleteNoticeById(noticeId);
	}

	/**
	 * 批量删除公告信息
	 *
	 * @param noticeIds 需要删除的公告ID
	 * @return 结果
	 */
	@Override
	public int deleteNoticeByIds(Long[] noticeIds) {
		return newsMapper.deleteNoticeByIds(noticeIds);
	}
}
