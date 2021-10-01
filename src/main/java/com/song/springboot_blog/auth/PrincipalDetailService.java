package com.song.springboot_blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;

@Service //Bean 등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로 챌때, username, password 변수 2개를 가로채는데
	// password 부분처리는 알아서함.
	// username이 DB에 있는지만 확인해주면 됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User principal = userRepository.findByUsername(username)
			.orElseThrow(()->{
				return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다:"+username);
			});
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저정보가 저장이 됨. 그냥 userDetail에 값을 따로 넣어주지 않으면 아이디: user, pw:콘솔 창으로 디폴트 세팅이 된다.
	}
}
