package com.alhtc.framework.manager.factory;

import com.alhtc.common.constant.Constants;
import com.alhtc.common.utils.LogUtils;
import com.alhtc.common.utils.ServletUtils;
import com.alhtc.common.utils.StringUtils;
import com.alhtc.common.utils.ip.AddressUtils;
import com.alhtc.common.utils.ip.IpUtils;
import com.alhtc.common.utils.spriing.SpringUtils;
import com.alhtc.system.domain.SysLoginInfo;
import com.alhtc.system.service.ISysLoginInfoService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author wangxiaoxu
 */
@Slf4j
public class AsyncFactory {

	/**
	 * 记录登录信息
	 *
	 * @param username 用户名
	 * @param status   状态
	 * @param message  消息
	 * @return 任务task
	 */
	public static TimerTask recordLoginInfo(final String username, final String status, final String message) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		return new TimerTask() {
			@Override
			public void run() {
				String address = AddressUtils.getRealAddressByIP(ip);
				String s = LogUtils.getBlock(ip) +
						address +
						LogUtils.getBlock(username) +
						LogUtils.getBlock(status) +
						LogUtils.getBlock(message);
				log.info(s);
				// 获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				// 封装对象
				SysLoginInfo loginInfo = new SysLoginInfo();
				loginInfo.setUserName(username);
				loginInfo.setIpaddr(ip);
				loginInfo.setLoginLocation(address);
				loginInfo.setBrowser(browser);
				loginInfo.setOs(os);
				loginInfo.setMsg(message);
				// 日志状态
				if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
					loginInfo.setStatus(Constants.SUCCESS);
				} else if (Constants.LOGIN_FAIL.equals(status)) {
					loginInfo.setStatus(Constants.FAIL);
				}
				SpringUtils.getBean(ISysLoginInfoService.class).insertLoginInfo(loginInfo);
			}
		};
	}
}
