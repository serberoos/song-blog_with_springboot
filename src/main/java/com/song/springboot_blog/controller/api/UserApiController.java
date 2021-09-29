package com.song.springboot_blog.controller.api;

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
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UserApiContriller : save 호출됨.");
		//실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		user.setRole(RoleType.USER);
		userService.회원가입(user); //result = 1 성공 , result = -1 실패
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//OK is 200
	}
}
