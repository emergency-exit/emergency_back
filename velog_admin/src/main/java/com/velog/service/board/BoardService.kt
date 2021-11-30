package com.velog.service.board

import com.velog.domain.board.Board
import com.velog.domain.board.repository.BoardRepository
import com.velog.domain.member.repository.MemberRepository
import com.velog.dto.board.RetrieveBoardRequest
import com.velog.dto.board.response.BoardInfoResponse
import com.velog.dto.board.response.BoardInfoWithHashTagResponse
import com.velog.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
open class BoardService(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    open fun retrieveBoard(request: RetrieveBoardRequest): List<BoardInfoResponse> {
        val sort = request.sort ?: "id"
        val pageable: Pageable = PageRequest.of(request.page - 1, request.size, Sort.by(DESC, sort))
        return boardRepository.findBySearchingPagination(pageable, request.search).stream()
            .map {
                lt -> BoardInfoResponse.of(lt)
            }.collect(Collectors.toList())
    }

    @Transactional
    open fun getBoard(boardId: Long): BoardInfoWithHashTagResponse {
        val board: Board = boardRepository.getBoardWithHashTag(boardId)
            .orElseThrow { throw NotFoundException("존재하지 않는 게시글 ${boardId} 입니다.") }
        val member = memberRepository.findMemberById(board.memberId)
            .orElseThrow { throw NotFoundException("존재하지 않는 유저 ${board.memberId} 입니다.") }
        return BoardInfoWithHashTagResponse.of(board, member)
    }

}