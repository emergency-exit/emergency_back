package com.velog.controller.board

import com.velog.controller.ApiResponse
import com.velog.dto.board.RetrieveBoardRequest
import com.velog.dto.board.response.BoardInfoResponse
import com.velog.dto.board.response.BoardInfoWithHashTagResponse
import com.velog.service.board.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping("/api/v1/board/list")
    fun retrieveBoard(@Valid request: RetrieveBoardRequest): ApiResponse<List<BoardInfoResponse>> {
        return ApiResponse.success(boardService.retrieveBoard(request))
    }

    @GetMapping("/api/v1/board/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ApiResponse<BoardInfoWithHashTagResponse> {
        return ApiResponse.success(boardService.getBoard(boardId))
    }

}