package com.song.springboot_blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

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
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
	private int id;
	
	@Column(nullable = false, length =100)
	private String title;
	
	@Lob // 대용량 데이터를 쓸때 사용
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨. 글자용량이 엄청큼

	@ColumnDefault("0") //int이므로 홀따옴표를 쓰지 않는다. String일 경우 DB에 알려주기 위해 홀 따옴표 사용
	private int count; // 조회수
	
	@ManyToOne // Board = Many, User = One 한명의 유저는 여러 게시글을 쓸 수 있다. | 연관관계
	@JoinColumn(name="userId") // 필드값은 One으로 만들어지고 연관관계는 Many To로 만들어짐
	private User user; //ORM에서는 키값으로 찾지 않고 오브젝트로 사용 (DB는 오브젝트를 사용 불가 FK 사용) => 여기서 자바와 DB가 충돌남 => 맞춰서 테이블을 만들게됨.ORM이 바꿈.
	
	@CreationTimestamp
	private Timestamp createDate;
}
