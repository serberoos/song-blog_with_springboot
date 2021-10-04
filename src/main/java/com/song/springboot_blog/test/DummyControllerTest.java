package com.song.springboot_blog.test;


import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.song.springboot_blog.model.RoleType;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //DummyControllerTest가 메모리에 뜰때, 같이 뜬다. (의존성 주입)DI
	private UserRepository userRepository;
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
	// email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id); //이렇게만 두면 없는 entity를 참조했을때, 오류가 발생하기 때문에 위험하다.(try catch 사용한다.)
		} catch (EmptyResultDataAccessException e) { //Exception = 예외 최상위 객체
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		
		return "삭제 되었습니다. id:"+id;
		
	}

	@Transactional // save함수를 쓸 필요없이 이 어노테이션을 붙이면 된다. => 이 방식을 더티 체킹이라고 한다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줘요.
		System.out.println("id :"+id);
		System.out.println("password :" + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		// save로 update를 수행하려면 아래처럼 람다 식을 사용해야함.
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
//		requestUser.setId(id);
//		requestUser.setUsername("song");
		//userRepository.save(user); <- save함수를 쓰는 것 말고 더 좋은 방법이 있음.
		//save함수는 원래 insert할 때 쓰는건데, 들어간 값에 id가 있으면 update 시켜준다.
		//하지만 이 경우 password와 email만 값이 있어서 다른 값들은 null로 변해버린다.
		//따라서 save로 update를 하려면 null 값을 채워주는 작업이 필요하다.
		
		// 더티 체킹 : Transaction 어노테이션을 걸면 데이터베이스에서 select를 해서 JPA 담은 후 그 값만 변경한 후 값이 바로 update된다.
		return null;
		
	}
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	// 한페이지당 2건에 데이터를 리턴 받아 볼 예정
	// user = 전체 user?page=0~... 하면 페이징 가능
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
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
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role: "+user.getRole());
		System.out.println("createDate: "+user.getCreateDate());
		
		
		user.setRole(RoleType.USER); //이런식으로 enum을 이용하도록 하면 개발자의 실수를 줄일 수 있다.
		userRepository.save(user); //데이터베이스(Mysql)에 회원정보 저장하기
		return "회원가입이 완료되었습니다.";
		// 이 때, role이 안들어 가는데 id 같은 경우에는 auto increment로 들어가게 되어 있고 createDate는 @CreateionTimestamp라는 어노테이션을 붙이면 스프링에서 넣어준다.
		// role은 기본 값이 User 테이블에 명시 되어 있어 User테이블이 메모리에 올라갈때 같이 올라가게 되어 있는데, 직접적으로 넣어주게 되면 null값이 들어가게 된다.
		// @DynamicInsert 를 이용 = insert할 때 null인 필드 제외
		
	}
}
