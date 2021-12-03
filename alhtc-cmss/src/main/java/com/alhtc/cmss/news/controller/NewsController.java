package com.alhtc.cmss.news.controller;

import com.alhtc.cmss.news.domain.model.SysNotice;
import com.alhtc.cmss.news.service.NewsService;
import com.alhtc.common.annotation.Log;
import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.page.TableDataInfo;
import com.alhtc.common.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/notice")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewsController extends BaseController {

	private final NewsService noticeService;

	/**
	 * 获取通知公告列表
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysNotice notice) {
		startPage();
		List<SysNotice> list = noticeService.selectNoticeList(notice);
		return getDataTable(list);
	}

	/**
	 * 根据通知公告编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:query')")
	@GetMapping(value = "/{noticeId}")
	public R getInfo(@PathVariable Long noticeId) {
		return R.success(noticeService.selectNoticeById(noticeId));
	}

	/**
	 * 新增通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:add')")
	@Log(title = "通知公告", businessType = BusinessType.INSERT)
	@PostMapping
	public R add(@Validated @RequestBody SysNotice notice) {
		notice.setCreateBy(getUsername());
		return toR(noticeService.insertNotice(notice));
	}

	/**
	 * 修改通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:edit')")
	@Log(title = "通知公告", businessType = BusinessType.UPDATE)
	@PutMapping
	public R edit(@Validated @RequestBody SysNotice notice) {
		notice.setUpdateBy(getUsername());
		return toR(noticeService.updateNotice(notice));
	}

	/**
	 * 删除通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:remove')")
	@Log(title = "通知公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeIds}")
	public R remove(@PathVariable Long[] noticeIds) {
		return toR(noticeService.deleteNoticeByIds(noticeIds));
	}
}
