package com.song.springboot_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping({"","/"}) //아무것도 안 붙였을 때와 /를 붙였을 때, 이동한다.
	public String index() {
		// /WEB-INF/views/index.jsp
		return "index";
	}

}
