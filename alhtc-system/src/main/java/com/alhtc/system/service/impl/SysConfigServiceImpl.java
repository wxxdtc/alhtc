package com.alhtc.system.service.impl;

import com.alhtc.common.constant.Constants;
import com.alhtc.common.core.redis.RedisCache;
import com.alhtc.common.core.text.Convert;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.system.domain.SysConfig;
import com.alhtc.system.mapper.SysConfigMapper;
import com.alhtc.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysConfigServiceImpl implements ISysConfigService {

	private final SysConfigMapper configMapper;

	private final RedisCache redisCache;

	/**
	 * 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
		loadingConfigCache();
	}

	@Override
	public boolean selectCaptchaOnOff() {
		String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
		if (StringUtils.isEmpty(captchaOnOff))
		{
			return true;
		}
		return Convert.toBool(captchaOnOff);
	}

	@Override
	public String selectConfigByKey(String configKey) {
		String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue))
		{
			return configValue;
		}
		SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
		SysConfig retConfig = configMapper.selectConfig(config);
		if (StringUtils.isNotNull(retConfig))
		{
			redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	@Override
	public void loadingConfigCache() {
		List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
		for (SysConfig config : configsList){
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 设置cache key
	 *
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
