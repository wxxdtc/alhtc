package com.alhtc.framework.config;

import com.alhtc.framework.security.filter.JwtAuthenticationTokenFilter;
import com.alhtc.framework.security.handle.AuthenticationEntryPointImpl;
import com.alhtc.framework.security.handle.LogoutSuccessHandlerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;


/**
 * spring security配置
 *
 * @author wangxiaoxu
 */
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 自定义用户认证逻辑
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * token认证过滤器
	 */
	private final JwtAuthenticationTokenFilter authenticationTokenFilter;

	/**
	 * 认证失败处理类
	 */
	private final AuthenticationEntryPointImpl unauthorizedHandler;

	/**
	 * 退出处理类
	 */
	private final LogoutSuccessHandlerImpl logoutSuccessHandler;

	/**
	 * 跨域过滤器
	 */
	private final CorsFilter corsFilter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// CSRF禁用，因为不使用session
				.csrf().disable()
				// 认证失败处理类
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 过滤请求
				.authorizeRequests()
				// 对于登录login 注册register 验证码captchaImage 允许匿名访问
				.antMatchers("/login", "/register", "/captchaImage").anonymous()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				.and()
				.headers().frameOptions().disable();
		httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
		// 添加JWT filter
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 添加CORS filter
		httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
		httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
	}

	/**
	 * 强散列哈希加密实现
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 身份认证接口
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("SecurityConfig.configure");
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
