package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardComment;
import com.velog.domain.board.repository.BoardCommentRepository;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.boardComment.request.BoardCommentRequest;
import com.velog.dto.boardComment.response.BoardCommentInfoResponse;
import com.velog.dto.member.response.MemberInfoResponse;
import com.velog.exception.NotFoundException;
import com.velog.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BoardCommentInfoResponse createBoardComment(BoardCommentRequest.CreateBoardComment request, Member member) {
        boardRepository.findBoardById(request.getBoardId())
                .orElseThrow(() -> new NotFoundException(String.format("%s는 존재하지 않는 게시글입니다.", request.getBoardId())));
        if (request.getParentCommentId() != null) {
            BoardComment boardComment = boardCommentRepository.findBoardCommentById(request.getParentCommentId())
                    .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 댓글 (%s) 입니다.", request.getParentCommentId())));
            BoardComment boardChildComment = boardComment.addChildComment(member.getId(), request.getContent(), request.getBoardId());
            return BoardCommentInfoResponse.of(boardChildComment, MemberInfoResponse.of(member));
        }
        BoardComment boardComment = boardCommentRepository.save(BoardComment.newRootComment(request.getBoardId(), request.getContent(), member.getId()));
        return BoardCommentInfoResponse.of(boardComment, MemberInfoResponse.of(member));
    }

    @Transactional
    public BoardCommentInfoResponse updateBoardComment(BoardCommentRequest.UpdateBoardComment request, Member member) {
        BoardComment boardComment = boardCommentRepository.findBoardCommentById(request.getBoardCommentId())
                .orElseThrow(() -> new NotFoundException(String.format("%s는 존재하지 않는 댓글입니다.", request.getBoardCommentId())));
        boardComment.updateContent(request.getContent());
        MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(member);
        return BoardCommentInfoResponse.of(boardComment, memberInfoResponse);
    }

    @Transactional
    public List<BoardCommentInfoResponse> retrieveBoardComment(Long boardId) {
        boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("%s는 존재하지 않는 게시글입니다.", boardId)));
        List<BoardComment> boardCommentList = boardCommentRepository.findBoardCommentByBoardId(boardId);
        return boardCommentList.stream().map(boardComment -> BoardCommentInfoResponse.of(boardComment,
                getMemberInfoResponse(boardComment.getMemberId())))
                .collect(Collectors.toList());
    }

    private MemberInfoResponse getMemberInfoResponse(Long memberId) {
        final Optional<Member> member = memberRepository.findMemberById(memberId);
        if (member.isEmpty()) {
            return MemberInfoResponse.deleteMember();
        }
        return MemberInfoResponse.of(member.get());
    }


}
