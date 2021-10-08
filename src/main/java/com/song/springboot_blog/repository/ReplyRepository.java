package com.song.springboot_blog.repository;
import com.song.springboot_blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {

}
