package com.song.springboot_blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.song.springboot_blog.dto.ReplySaveRequestDto;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply { //답변 테이블
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀸스 , auto_increment
	
	@Column(nullable = false, length = 200)
	private String content;
	// 누가? 어느 테이블에? 연관 관계가 필요하다.
	
	@ManyToOne //여러 답변은 하나의 게시글에 적용될 수 있다.
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne //여러 답변을 한 유저가 쓸 수 있다.
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;

	public void update(User user, Board board, String content){
		setUser(user);
		setBoard(board);
		setContent(content);

	}
}
