package com.shop.onlineshop.config;

import com.shop.onlineshop.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private static final String API_ENDPOINT = "/api/**";
	private static final String AUTH_ENDPOINT = "/auth/**";
	private static final String ORDER_ENDPOINT = "/cart/**";
	private static final String PROFILE_ENDPOINT = "/profile/**";
 	private static final String ADMIN_ENDPOINT = "/admin/**/**";
	private static final String OAUTH_ENDPOINT = "/oauth/**";

	private final CustomOidcUserService customOidcUserService;
	
	private UserPrincipalDetailService userPrincipalDetailService;
	
	public SpringSecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService, @Lazy CustomOidcUserService customOidcUserService) {
		this.userPrincipalDetailService = userPrincipalDetailService;
		this.customOidcUserService = customOidcUserService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userPrincipalDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(AUTH_ENDPOINT).permitAll()
				.antMatchers(ORDER_ENDPOINT).authenticated()
				.antMatchers(PROFILE_ENDPOINT).authenticated()
				.antMatchers(OAUTH_ENDPOINT).permitAll()
				.antMatchers(API_ENDPOINT).permitAll()
				.anyRequest().permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutSuccessUrl("/auth/login")
			.and()
			.formLogin().loginPage("/auth/login")
				.usernameParameter("email")
				.passwordParameter("password")
			.and()
				.oauth2Login()
				.loginPage("/auth/login")
				.userInfoEndpoint()
				.oidcUserService(customOidcUserService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomOidcUserService customOidcUserService() {
		return new CustomOidcUserService();
	}
}
