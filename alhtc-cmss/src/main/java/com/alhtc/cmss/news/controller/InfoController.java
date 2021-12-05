package com.alhtc.cmss.news.controller;

import com.alhtc.cmss.news.domain.model.VillageInformation;
import com.alhtc.cmss.news.service.InfoService;
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
 * 信息操作处理
 *
 * @author lei.si
 */
@RestController
@RequestMapping("/village/information")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfoController extends BaseController {

	private final InfoService infoService;

	/**
	 * 获取信息发布列表
	 */
	@PreAuthorize("@ss.hasPermi('village:information:list')")
	@GetMapping("/list")
	public TableDataInfo list(VillageInformation information) {
		startPage();
		List<VillageInformation> list = infoService.selectInformationList(information);
		return getDataTable(list);
	}

	/**
	 * 根据信息发布编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('village:information:query')")
	@GetMapping(value = "/{informationId}")
	public R getInfo(@PathVariable Long informationId) {
		return R.success(infoService.selectInformationById(informationId));
	}

	/**
	 * 新增信息发布
	 */
	@PreAuthorize("@ss.hasPermi('village:information:add')")
	@Log(title = "信息发布", businessType = BusinessType.INSERT)
	@PostMapping
	public R add(@Validated @RequestBody VillageInformation information) {
		information.setCreateBy(getUsername());
		return toR(infoService.insertInformation(information));
	}

	/**
	 * 修改信息发布
	 */
	@PreAuthorize("@ss.hasPermi('village:information:edit')")
	@Log(title = "信息发布", businessType = BusinessType.UPDATE)
	@PutMapping
	public R edit(@Validated @RequestBody VillageInformation information) {
		information.setUpdateBy(getUsername());
		return toR(infoService.updateInformation(information));
	}

	/**
	 * 删除信息发布
	 */
	@PreAuthorize("@ss.hasPermi('village:information:remove')")
	@Log(title = "信息发布", businessType = BusinessType.DELETE)
	@DeleteMapping("/{informationIds}")
	public R remove(@PathVariable Long[] informationIds) {
		return toR(infoService.deleteInformationByIds(informationIds));
	}
}
