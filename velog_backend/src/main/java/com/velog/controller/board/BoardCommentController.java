package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.boardComment.request.BoardCommentRequest;
import com.velog.dto.boardComment.response.BoardCommentInfoResponse;
import com.velog.service.board.BoardCommentService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @ApiOperation(value = "게시글 댓글 생성", notes = "게시글 댓글 생성")
    @PostMapping("/api/v1/board/comment")
    public ApiResponse<BoardCommentInfoResponse> createBoardComment(@RequestBody @Valid BoardCommentRequest.CreateBoardComment request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardCommentService.createBoardComment(request, principalDetails.getMember()));
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정")
    @PostMapping("/api/v1/board/comment/update")
    public ApiResponse<BoardCommentInfoResponse> updateBoardComment(@RequestBody @Valid BoardCommentRequest.UpdateBoardComment request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardCommentService.updateBoardComment(request, principalDetails.getMember()));
    }

    @ApiOperation(value = "게시글 댓글 불러오기", notes = "게시글 댓글 불러오기")
    @GetMapping("/board/comment/list/{boardId}")
    public ApiResponse<List<BoardCommentInfoResponse>> retrieveBoardComment(@PathVariable Long boardId) {
        return ApiResponse.success(boardCommentService.retrieveBoardComment(boardId));
    }

}
