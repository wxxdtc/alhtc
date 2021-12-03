package com.alhtc.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.alhtc.common.constant.HttpStatus;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.utils.ServletUtils;
import com.alhtc.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author wangxiaoxu
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		int code = HttpStatus.UNAUTHORIZED;
		String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
		ServletUtils.renderString(response, JSON.toJSONString(R.error(code, msg)));

	}
}
