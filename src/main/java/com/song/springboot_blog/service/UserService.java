package com.song.springboot_blog.service;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 .IOC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public int 회원가입(User user) { //전체 트랜잭션 들이 성공하면 업데이트가 되고 실패하면 롤백이 될 것이다.
		try { //여러 트랜잭션이 모여서 하나의 서비스가 될 수 있다.
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입()"+e.getMessage());
		}
		return -1;
	}
}
