package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public BoardComment(Long boardId, Long memberId, String content) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
