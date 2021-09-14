package com.song.springboot_blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일 리턴 기본 경로 : src/main/resources/static
		//리턴명을 /home.html 이라고 해야한다.
		//풀경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/folder.PNG";
	}
	//얘는 static 폴더에 두면 못찾음 왜냐면 컴파일 해야하는 java 동적 파일이라서..
	@GetMapping("/temp/jsp") 
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//풀네임 : /WEB-INF/views/test.jsp
		return "testfile";
	}
}
