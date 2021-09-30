package com.song.springboot_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.song.springboot_blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
//@Repository // 생략 가능 하다. 
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA Naming 전략
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;
	User findByUsernameAndPassword(String username, String password); // 1번째 방법
	
//	@Query(value="SELECT * FROM user WHERE username =?1 AND password =?2", nativeQuery = true) // 2번째 방법
//	User login(String username, String password);
}
