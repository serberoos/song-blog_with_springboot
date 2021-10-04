package com.song.springboot_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.song.springboot_blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"","/"}) //아무것도 안 붙였을 때와 /를 붙였을 때, 이동한다.
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { 
		/* @AuthenticationPrincipal PrincipalDetail principal */
		//컨트롤러에서 세션을 어떻게 찾는지? => 세션에 접근할 땐 이렇게 접근하면 된다.
		model.addAttribute("boards",boardService.글목록(pageable));
		// /WEB-INF/views/index.jsp
		return "index"; //viewResolver 작동!
	}
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

}
