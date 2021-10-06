package com.song.springboot_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.springboot_blog.model.RoleType;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 .IOC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired // DI 주인이 된다.
	private BCryptPasswordEncoder encoder;

	@Transactional // 여러 트랜잭션이 모여서 하나의 서비스가 될 수 있다.
	public void 회원가입(User user) { // 전체 트랜잭션 들이 성공하면 업데이트가 되고 실패하면 롤백이 될 것이다.
		String rawPassword = user.getPassword(); //1234 원문
		String encPassword = encoder.encode(rawPassword); //해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원정보수정(User user) {
		// 수정 시에는 영속성 컨텍스트 User오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
		User persistance =userRepository.findById(user.getId()).orElseThrow(()->{ // username으로 해당 유저 오브젝트를 영속화 시킨다.
			return new IllegalArgumentException("회원 찾기 실패");
		});

		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//회원 수정 함수 종료 시 = 서비스 종료시 = 트랜잭션 종료 = commit이 자동으로 됩니다.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.

		//DB는 해당 서비스가 끝날때 commit 되므로 여기서 세션에 업데이트를 할 수 없다.
	}
	
//	@Transactional(readOnly = true) // Select할 떄 트랜잭션 시장, 서비스 종료시에 트랜잭션 종료 ( 정합성 )
//	public User 로그인(User user) {
//		
//		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
}
