package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.domain.board.Series;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/v1/series")
    public ApiResponse<SeriesResponse> createSeries(@RequestBody BoardRequest.CreateSeries request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createSeries(request, principalDetails.getMember().getEmail().getEmail()));
    }

}
