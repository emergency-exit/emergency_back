package com.velog.service.board;

import com.velog.domain.board.Series;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.board.request.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;

    @Transactional
    public Series createSeries(BoardRequest.CreateSeries request, String email) {
        Member member = memberRepository.findByEmail(email);
        return member.addSeries(request.getSeriesName());
    }

}
