package com.song.springboot_blog.test;

import com.song.springboot_blog.model.Board;
import com.song.springboot_blog.model.Reply;
import com.song.springboot_blog.repository.BoardRepository;
import com.song.springboot_blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id){
        return boardRepository.findById(id).get(); //jackson 라이브러리
        // (오브젝트를 json으로 리턴) => 모델의 Getter를 호출한다.
    }

    @GetMapping("/test/reply")
    public List<Reply> getReply(@PathVariable int id){ //이런식으로 다이렉트로 접근하면 board 테이블도 함께 준다. 
        return replyRepository.findAll(); //jackson 라이브러리
        // (오브젝트를 json으로 리턴) => 모델의 Getter를 호출한다.
    }
}
