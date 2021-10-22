package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoResponse;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "시리즈 생성", notes = "시리즈 생성")
    @PostMapping("/api/v1/series")
    public ApiResponse<SeriesResponse> createSeries(@RequestBody BoardRequest.CreateSeries request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createSeries(request, principalDetails.getMember().getEmail().getEmail()));
    }

    @ApiOperation(value = "시리즈 리스트", notes = "시리즈 리스트")
    @GetMapping("/api/v1/series")
    public ApiResponse<List<SeriesResponse>> retrieveSeries(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.retrieveSeries(principalDetails.getMember().getId()));
    }

    @ApiOperation(value = "게시글 생성", notes = "게시글 생성")
    @PostMapping("/api/v1/board")
    public ApiResponse<BoardInfoResponse> createBoard(@Valid @RequestBody BoardRequest.CreateBoard request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.createBoard(request, principalDetails.getMember().getId()));
    }

    @ApiOperation(value = "게시글 조회", notes = "게시글 조회, 오늘, 이번주, 이번달, 올해, 최신")
    @GetMapping("/board/list")
    public ApiResponse<List<BoardRetrieveResponse>> retrieveBoard(@Valid BoardRequest.RetrieveBoardRequest request) {
        List<BoardRetrieveResponse> boardList = boardService.retrieveBoard(request.getLastBoardId(), request.getSize(), request.getPeriod(), null);
        return ApiResponse.success(boardList);
    }

    @ApiOperation(value = "게시글 상세보기", notes = "게시글 상세보기")
    @GetMapping("/board/detail/{boardId}")
    public ApiResponse<BoardInfoWithHashTagResponse> getBoard(@PathVariable Long boardId) {
        return ApiResponse.success(boardService.getBoard(boardId));
    }

    @ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요")
    @PostMapping("/api/v1/board/like")
    public ApiResponse<String> boardLike(@Valid @RequestBody BoardRequest.GetBoardRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        boardService.boardLike(request.getBoardId(), principalDetails.getMember().getId());
        return ApiResponse.OK;
    }

    @ApiOperation(value = "게시글 좋아요 취소", notes = "게시글 좋아요 취소")
    @PostMapping("/api/v1/board/unlike")
    public ApiResponse<String> boardUnlike(@Valid @RequestBody BoardRequest.GetBoardRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        boardService.boardUnLike(request.getBoardId(), principalDetails.getMember().getId());
        return ApiResponse.OK;
    }

}
