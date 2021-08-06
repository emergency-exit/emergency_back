package com.velog.service.board;

import com.velog.domain.board.Series;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.SeriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;

    @Transactional
    public SeriesResponse createSeries(BoardRequest.CreateSeries request, String email) {
        Member member = memberRepository.findByEmail(email);
        Series series = member.addSeries(request.getSeriesName());
        return SeriesResponse.of(series.getSeriesName());
    }

}
