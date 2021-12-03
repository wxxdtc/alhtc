package com.alhtc.web.system;

import com.alhtc.common.core.controller.BaseController;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.domain.entity.SysDictData;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.service.ISysDictDataService;
import com.alhtc.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDictDataController extends BaseController {

	private final ISysDictDataService dictDataService;

	private final ISysDictTypeService dictTypeService;

	/**
	 * 根据字典类型查询字典数据信息
	 */
	@GetMapping(value = "/type/{dictType}")
	public R dictType(@PathVariable String dictType) {
		List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
		if (StringUtils.isNull(data)) {
			data = new ArrayList<>();
		}
		return R.success(data);
	}
}
