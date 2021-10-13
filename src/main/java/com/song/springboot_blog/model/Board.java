package com.song.springboot_blog.model;

import java.sql.Timestamp;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
//ORM -> Java(다른 언어) Object -> 테이블로 매핑 해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터를 쓸때 사용
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨. 글자용량이 엄청큼

//	@ColumnDefault("0") //int이므로 홀따옴표를 쓰지 않는다. String일 경우 DB에 알려주기 위해 홀 따옴표 사용
	private int count; // 조회수

	// Board = Many, User = One 한명의 유저는 여러 게시글을 쓸 수 있다. | 연관관계
	@ManyToOne(fetch = FetchType.EAGER) // Many = Many, User = One // EAGER 전략 : 무조건 얘 들고와
	@JoinColumn(name = "userId") // 필드값은 One으로 만들어지고 연관관계는 Many To로 만들어짐
	private User user; // ORM에서는 키값으로 찾지 않고 오브젝트로 사용 (DB는 오브젝트를 사용 불가 FK 사용) => 여기서 자바와 DB가 충돌남 => 맞춰서
						// 테이블을 만들게됨.ORM이 바꿈.

	// 한개의 게시글에 여러 댓글이 존재할 수 있다.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy가 적혀있으면 연관관계의 주인이 아니다(난 FK가 아니에요) DB에 칼럼을 만들지
	// Board 게시글을 제거할 때 댓글을 전부 삭제하겠다.
															// 마세요.
	// LAZY 전략 필요할 때 들고 와 하지만 Board들고 올때 다른것도 다 들고와야 되기 때문에 EAGER 전략으로 바꾸어주어야 한다.
	// fetch = FetchType.LAZY => EAGER
	// @JoinColumn(name="replyId") FK는 DB에 있어야 한다. 원자성이 깨짐
	@JsonIgnoreProperties({"board","user"})
	@OrderBy("id desc")
	private List<Reply> replys; // board를 select할 때, FK를 이용해서 값을 얻기 위한 것이다.

	@CreationTimestamp
	private Timestamp createDate;
}
