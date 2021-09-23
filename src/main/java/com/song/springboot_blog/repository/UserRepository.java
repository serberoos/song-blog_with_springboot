package com.song.springboot_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.springboot_blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
//@Repository // 생략 가능 하다. 
public interface UserRepository extends JpaRepository<User, Integer> {

}
