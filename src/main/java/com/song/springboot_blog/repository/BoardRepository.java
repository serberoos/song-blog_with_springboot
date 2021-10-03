package com.song.springboot_blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.springboot_blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
