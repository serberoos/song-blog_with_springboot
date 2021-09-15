package com.song.springboot_blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
//ORM -> Java(다른 언어) Object -> 테이블로 매핑 해주는 기술
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀸스 , auto_increment
	
	@Column(nullable = false, length =30)
	private String username; //아이디
	
	@Column(nullable = false, length = 100) //123456 => 해쉬 ( 비밀번호 암호화 ) 넉넉하게 100으로 잡는다.
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault(" 'user' ") //문자라는 걸 알려줘야 함
	private String role; //Enum을 쓰는게 좋다. // admin, user, manager 권한 타입이 스트링이면 이상한 값이 들어갈 수 있다. 도메인 : 어떤 범위가 정해졌다.
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
}
