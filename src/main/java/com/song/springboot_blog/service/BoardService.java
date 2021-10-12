package com.song.springboot_blog.service;

import com.song.springboot_blog.dto.ReplySaveRequestDto;
import com.song.springboot_blog.repository.ReplyRepository;
import com.song.springboot_blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.song.springboot_blog.model.Board;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor //final은 초기화가 필요한데 이걸 붙이면 자동으로 초기화를 해준다.
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

/*    public BoardService(BoardRepository bRepo, ReplyRepository rRepo){ //@Autowired와 동일 한 것이다.
        this.boardRepository =bRepo;
        this.replyRepository=rRepo;
    }*/
/*    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;*/


    @Transactional
    public void 글쓰기(Board board, User user) { //title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
        int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
        System.out.println(result);

    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById((id)).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById((id))
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                }); //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시에 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가 db flush
    }
}
