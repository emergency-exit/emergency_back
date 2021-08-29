package com.velog.dto.boardComment.request;

import com.velog.domain.board.BoardComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardCommentRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateBoardComment {

        private Long boardId;
        private String content;

        public CreateBoardComment(Long boardId, String content) {
            this.boardId = boardId;
            this.content = content;
        }

        public BoardComment toEntity(Long memberId) {
            return new BoardComment(boardId, memberId, content);
        }

        public static CreateBoardComment testInstance(Long boardId, String content) {
            return new CreateBoardComment(boardId, content);
        }

    }

    @Getter
    @NoArgsConstructor
    public static class UpdateBoardComment {

        private Long boardCommentId;
        private String content;

        public UpdateBoardComment(Long boardCommentId, String content) {
            this.boardCommentId = boardCommentId;
            this.content = content;
        }

        public static UpdateBoardComment testInstance(Long boardCommentId, String content) {
            return new UpdateBoardComment(boardCommentId, content);
        }

    }


}
