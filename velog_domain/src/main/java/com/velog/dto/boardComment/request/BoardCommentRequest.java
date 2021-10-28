package com.velog.dto.boardComment.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardCommentRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateBoardComment {

        private Long boardId;
        private String content;
        private Long parentCommentId;

        public CreateBoardComment(Long boardId, String content, Long parentCommentId) {
            this.boardId = boardId;
            this.content = content;
            this.parentCommentId = parentCommentId;
        }

        public static CreateBoardComment rootTestInstance(Long boardId, String content) {
            return new CreateBoardComment(boardId, content, null);
        }

        public static CreateBoardComment childTestInstance(Long boardId, String content, Long parentCommentId) {
            return new CreateBoardComment(boardId, content, parentCommentId);
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
