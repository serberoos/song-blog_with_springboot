package com.song.springboot_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping({"","/"}) //아무것도 안 붙였을 때와 /를 붙였을 때, 이동한다.
	public String index(/* @AuthenticationPrincipal PrincipalDetail principal */) { //컨트롤러에서 세션을 어떻게 찾는지? => 세션에 접근할 땐 이렇게 접근하면 된다.
		// /WEB-INF/views/index.jsp
	
		return "index";
	}
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

}
