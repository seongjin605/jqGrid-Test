package com.jqgrid.util.security;

import java.beans.Encoder;

import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityWebApplicationInitializer extends WebSecurityConfigurerAdapter {

	@Inject
	private UserLoginSuccessHandler userLoginSuccessHandler;
	
	@Inject
	private UserLoginFailureHandler userLoginFailureHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
		.authorizeRequests()
			.antMatchers("/**","/resources/**").permitAll()
			.anyRequest().permitAll()
			.and()
		.formLogin()
			.loginPage("/user/login" )
			.loginProcessingUrl("/user/loginSuccess")
			.defaultSuccessUrl("/main", true)
			.successHandler(userLoginSuccessHandler)
			.failureHandler(userLoginFailureHandler)
			.usernameParameter("mid")
			.passwordParameter("mpwd")
			.permitAll()
			.and()
		.logout()
			.deleteCookies("JSESSIONID,SPRING_SECURITY_REMEMBER_ME_COOKIE")
			.logoutSuccessUrl("/user/login")
			.logoutUrl("/user/logout")
			.invalidateHttpSession(true)
			.and()
		.rememberMe()
			.key("nieeKey")
			.tokenValiditySeconds(604800)
			.and()
			.csrf().disable().httpBasic().and()
		.sessionManagement()
			.maximumSessions(1)
			.expiredUrl("/main")
			.maxSessionsPreventsLogin(true);
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT mid as username, mpwd as password,ENABLED as enabled​ FROM user WHERE mid=?")
			.authoritiesByUsernameQuery("SELECT mid as username, AUTHORITY AS authority  FROM user WHERE mid=?");
	
	}
/*	@Override
	protected UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return userDetailsService();
	}*/
	
	@Bean(name="passwordEncoder")
	public ShaPasswordEncoder ShaPasswordEncoder() throws Exception {

		return new ShaPasswordEncoder(256);
		
	}
	
}
