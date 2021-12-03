package com.alhtc.framework.security.handle;

import com.alhtc.common.core.domain.model.LoginUser;
import com.alhtc.framework.web.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author wangxiaoxu
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	private final TokenService tokenService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LoginUser loginUser = tokenService.getLoginUser(request);
	}
}
