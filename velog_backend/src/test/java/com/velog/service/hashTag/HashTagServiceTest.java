package com.velog.service.hashTag;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardHashTag;
import com.velog.domain.board.repository.BoardHashTagRepository;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.BoardCreator;
import com.velog.domain.testObject.HashTagCreator;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.exception.NotFoundException;
import com.velog.service.board.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class HashTagServiceTest {

    @Autowired
    private BoardHashTagRepository boardHashTagRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardService boardService;

    @AfterEach
    void clean() {
        boardRepository.deleteAll();
//        boardHashTagRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 게시글_상세보기_해쉬태그_리스트까지_가져온다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        BoardHashTag hashTag1 = HashTagCreator.create("java", member.getId(), board);
        BoardHashTag hashTag2 = HashTagCreator.create("potato", member.getId(), board);
        BoardHashTag hashTag3 = HashTagCreator.create("msa", member.getId(), board);

        boardHashTagRepository.saveAll(Arrays.asList(hashTag1, hashTag2, hashTag3));

        // when
        BoardInfoWithHashTagResponse boardInfoWithHashTagResponse = boardService.getBoard(board.getId());

        // then
        assertThat(boardInfoWithHashTagResponse.getHashTagList()).hasSize(3);
        assertThat(boardInfoWithHashTagResponse.getHashTagList().get(0).getHashTag()).isEqualTo(hashTag1.getHashTag());
    }

    @Test
    void 게시글_상세보기_해쉬태그가_없으면_빈배열_반환() {
        Member member = MemberCreator.create();
        memberRepository.save(member);

        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        // when
        BoardInfoWithHashTagResponse boardInfoWithHashTagResponse = boardService.getBoard(board.getId());

        // then
        assertThat(boardInfoWithHashTagResponse.getHashTagList()).isEmpty();
    }

    @Test
    void 존재하지_않는_게시글일_경우_예외발생() {
        // when
        assertThatThrownBy(
            () -> boardService.getBoard(1L)
        ).isInstanceOf(NotFoundException.class);
    }

}
