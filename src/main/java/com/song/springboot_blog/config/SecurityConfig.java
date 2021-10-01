package com.song.springboot_blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.song.springboot_blog.auth.PrincipalDetailService;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC가 된다. => 리턴값을 스프링이 관리한다.
	public BCryptPasswordEncoder encodePWD() {
	
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인 해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
		.authorizeRequests() //요청이 들어오면
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // auth쪽으로 들어오면 
			.permitAll() //허용해준다.
			.anyRequest() // 이거 외의 모든 요청은
			.authenticated() // 인증이 되어야 한다.
		.and()
			.formLogin() // 인증이 필요한 곳으로 요청이 오면
			.loginPage("/auth/loginForm") // loginForm으로 인도한다.
			.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
			.defaultSuccessUrl("/"); // 정상적으로 요청이 완료가 되면 / 로 이동한다.
//			.failureUrl("/fail"); // 실패할시에는 /fail로 간다.
	}
	
}
