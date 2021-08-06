package com.velog.service.board;

import com.velog.domain.board.Series;
import com.velog.domain.board.repository.SeriesRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.board.request.BoardRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
        seriesRepository.deleteAll();
    }

    @Test
    void 멤버가_시리즈를_생성한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateSeries request = new BoardRequest.CreateSeries("자바");

        // when
        boardService.createSeries(request, member.getEmail().getEmail());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(1);
    }

}
