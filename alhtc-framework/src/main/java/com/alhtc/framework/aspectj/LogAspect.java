package com.alhtc.framework.aspectj;

import com.alhtc.common.annotation.Log;
import com.alhtc.common.enums.BusinessStatus;
import com.alhtc.common.utils.ServletUtils;
import com.alhtc.common.utils.ip.IpUtils;
import com.alhtc.system.domain.SysOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录处理
 *
 * @author wangxiaoxus
 */
@Aspect
@Component
public class LogAspect {

	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
		try {
			// 获取当前的用户
//			LoginUser loginUser = SecurityUtils.getLoginUser();

			// *========数据库日志=========*//
			SysOperLog operLog = new SysOperLog();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 请求的地址
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
			operLog.setOperIp(ip);
			operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());

		} catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}
}
