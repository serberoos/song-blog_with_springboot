package com.song.springboot_blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.song.springboot_blog.dto.ResponseDto;
import com.song.springboot_blog.model.RoleType;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	/*
	 * @Autowired private HttpSession session; // 스프링 컨트롤러에서 기본으로 가지고 있다. 따라서
	 * Autowired로 가져올 수 있음.
	 */	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨.");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		user.setRole(RoleType.USER);
		userService.회원가입(user); // result = 1 성공 , result = -1 실패
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);// OK is 200
	}

	/*
	 * // 이러한 방식은 스프링 전통적인 방식으로 요즘은 스프링 시큐리티를 이용해서 로그인을 구현한다.
	 * 
	 * @PostMapping("api/user/login") public ResponseDto<Integer> login(@RequestBody
	 * User user) { //, HttpSession session 파라미터를 추가해 쓸 수도 있다.
	 * System.out.println("User ApiController : login 호출됨"); User principal =
	 * userService.로그인(user); // principal 접근 주체
	 * 
	 * if (principal != null) { session.setAttribute("principal",principal); }
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */
}
