package com.song.springboot_blog.repository;
import com.song.springboot_blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ReplyRepository extends JpaRepository<Reply,Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId,boardId,content,createDate) VALUES(?1,?2,?3,now())",nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 업데이트 된 행의 개수를 리턴해줌.
}
