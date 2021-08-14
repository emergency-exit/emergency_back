package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.domain.board.Board;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoResponse;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "시리즈 생성", notes = "시리즈 생성")
    @PostMapping("/api/v1/series")
    public ApiResponse<SeriesResponse> createSeries(@RequestBody BoardRequest.CreateSeries request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createSeries(request, principalDetails.getMember().getEmail().getEmail()));
    }

    @ApiOperation(value = "게시글 생성", notes = "게시글 생성")
    @PostMapping("/api/v1/board")
    public ApiResponse<BoardInfoResponse> createBoard(@Valid @RequestBody BoardRequest.CreateBoard request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createBoard(request, principalDetails.getMember().getId()));
    }

    @ApiOperation(value = "게시글 조회", notes = "게시글 조회, 오늘, 이번주, 이번달, 올해, 최신")
    @GetMapping("/board/list")
    public ApiResponse<List<BoardInfoResponse>> retrieveBoard(BoardRequest.RetrieveBoardRequest request) {
        List<Board> boardList = boardService.retrieveBoard(request.getLastBoardId(), request.getSize(), request.getPeriod());
        return ApiResponse.success(boardList.stream().map(BoardInfoResponse::of).collect(Collectors.toList()));
    }

}
