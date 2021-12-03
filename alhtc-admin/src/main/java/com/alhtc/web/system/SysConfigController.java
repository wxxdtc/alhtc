package com.alhtc.web.system;

import com.alhtc.common.core.domain.R;
import com.alhtc.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置 信息操作处理
 *
 * @author wangxiaoxu
 */
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysConfigController {

	private final ISysConfigService configService;

	/**
	 * 根据参数键名查询参数值
	 */
	@GetMapping(value = "/configKey/{configKey}")
	public R getConfigKey(@PathVariable String configKey)
	{
		return R.success(configService.selectConfigByKey(configKey));
	}

}
