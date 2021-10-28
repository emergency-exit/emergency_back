package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import com.velog.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private BoardComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private final List<BoardComment> childComments = new ArrayList<>();

    @Column(nullable = false)
    private int depth;

    public BoardComment(Long boardId, Long memberId, String content, BoardComment parentComment, int depth) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.content = content;
        this.parentComment = parentComment;
        this.depth = depth;
    }

    public static BoardComment newRootComment(Long boardId, String content, Long memberId) {
        return new BoardComment(boardId, memberId, content, null, 0);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public BoardComment addChildComment(Long memberId, String content, Long boardId) {
        if (this.parentComment != null || this.depth == 1) {
            throw new ValidationException("댓글은 1 depth 까지 가능합니다.");
        }
        BoardComment boardChildComment = new BoardComment(boardId, memberId, content, this, this.depth + 1);
        this.childComments.add(boardChildComment);
        return boardChildComment;
    }

}
