package com.alhtc.system.service;

/**
 * 参数配置 服务层
 *
 * @author wangxiaoxu
 */
public interface ISysConfigService {

	/**
	 * 获取验证码开关
	 *
	 * @return true开启，false关闭
	 */
	boolean selectCaptchaOnOff();

	/**
	 * 根据键名查询参数配置信息
	 *
	 * @param configKey 参数键名
	 * @return 参数键值
	 */
	String selectConfigByKey(String configKey);

	/**
	 * 加载参数缓存数据
	 */
	void loadingConfigCache();

}
