package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoResponse;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/v1/series")
    public ApiResponse<SeriesResponse> createSeries(@RequestBody BoardRequest.CreateSeries request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createSeries(request, principalDetails.getMember().getEmail().getEmail()));
    }

    @PostMapping("/api/v1/board")
    public ApiResponse<BoardInfoResponse> createBoard(@Valid @RequestBody BoardRequest.CreateBoard request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        boardService.createBoard(request, principalDetails.getMember().getId());
        return ApiResponse.success(boardService.createBoard(request, principalDetails.getMember().getId()));
    }

}
