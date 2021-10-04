package com.song.springboot_blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.song.springboot_blog.model.Board;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.BoardRepository;


@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional 
	public void 글쓰기(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
}
