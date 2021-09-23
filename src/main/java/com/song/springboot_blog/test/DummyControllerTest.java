package com.song.springboot_blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song.springboot_blog.model.RoleType;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //DummyControllerTest가 메모리에 뜰때, 같이 뜬다. (의존성 주입)DI
	private UserRepository userRepository;
	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 요청하게 되면...
	@PostMapping("/dummy/join") //key=value(약속된 규칙 : x-www-form-urlencoded
	public String join(User user) {
		System.out.println("id:"+user.getId());
		System.out.println("username"+user.getUsername());
		System.out.println("password"+user.getPassword());
		System.out.println("email"+user.getEmail());
		System.out.println("role:"+user.getRole());
		System.out.println("createDate:"+user.getCreateDate());
		
		
		user.setRole(RoleType.USER); //이런식으로 enum을 이용하도록 하면 개발자의 실수를 줄일 수 있다.
		userRepository.save(user); //데이터베이스(Mysql)에 회원정보 저장하기
		return "회원가입이 완료되었습니다.";
		// 이 때, role이 안들어 가는데 id 같은 경우에는 auto increment로 들어가게 되어 있고 createDate는 @CreateionTimestamp라는 어노테이션을 붙이면 스프링에서 넣어준다.
		// role은 기본 값이 User 테이블에 명시 되어 있어 User테이블이 메모리에 올라갈때 같이 올라가게 되어 있는데, 직접적으로 넣어주게 되면 null값이 들어가게 된다.
		// @DynamicInsert 를 이용 = insert할 때 null인 필드 제외
		
	}
}
