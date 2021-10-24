package com.velog.controller.member;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import com.velog.dto.member.request.UpdateMemberRequest;
import com.velog.dto.member.response.MemberInfoResponse;
import com.velog.service.board.BoardService;
import com.velog.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    public ApiResponse<String> createMember(@Valid @RequestBody CreateMemberRequest request) {
        memberService.createMember(request);
        return ApiResponse.success("ok");
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(memberService.login(request));
    }

    @ApiOperation(value = "내 정보 조회", notes = "내 정보 조회")
    @GetMapping("/api/v1/myInfo")
    public ApiResponse<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(MemberInfoResponse.of(principalDetails.getMember()));
    }

    @ApiOperation(value = "내 정보 수정", notes = "내 정보 수정")
    @PutMapping("/api/v1/myInfo/update")
    public ApiResponse<MemberInfoResponse> updateMyInfo(@Valid @RequestBody UpdateMemberRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(memberService.updateMember(request, principalDetails.getMember().getEmail().getEmail()));
    }

    @ApiOperation(value = "내가 쓴 게시글리스트 가져오기", notes = "내가 쓴 게시글")
    @GetMapping("/api/v1/myInfo/board/list")
    public ApiResponse<List<BoardRetrieveResponse>> retrieveMyBoard(@Valid BoardRequest.RetrieveBoardRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<BoardRetrieveResponse> boardRetrieveResponseList = boardService.retrieveBoard(request.getLastBoardId(), request.getSize(), request.getPeriod(), principalDetails.getMember().getId());
        return ApiResponse.success(boardRetrieveResponseList);
    }

    @ApiOperation(value = "내가 쓴 특정 게시글 가져오기", notes = "내가 쓴 특정 게시글")
    @GetMapping("/api/v1/myInfo/board/detail/{boardId}")
    public ApiResponse<BoardInfoWithHashTagResponse> getMyBoard(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardService.getMyBoard(boardId, principalDetails.getMember().getId()));
    }

}
