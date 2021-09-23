package com.song.springboot_blog.test;


import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song.springboot_blog.model.RoleType;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //DummyControllerTest가 메모리에 뜰때, 같이 뜬다. (의존성 주입)DI
	private UserRepository userRepository;
	
	// {id} 주소로 파라미터를 전달 받을 수 있다.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
// user /4번을 찾으면 내가 DB에서 못찾으면 user가 null이 되니까 Optional로 User객체를 감싸서 가져올 테니 null인지 아닌지 판단해서 리턴할 것
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User();
//			}
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() { //User를 넣을 경우 전부 null이 리턴됨

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다 id :" + id); //람다식으로 하면 더 편할 수 있다.
			}
		});
		// 요청 : 웹 브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터 -> json (Gson 라이브러리)
		// 스프링 부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}

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
