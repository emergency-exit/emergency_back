package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardHashTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String hashTag;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardHashTag(String hashTag, Long memberId, Board board) {
        this.hashTag = hashTag;
        this.memberId = memberId;
        this.board = board;
    }

    public static BoardHashTag of(String hashTag, Long memberId, Board board) {
        return new BoardHashTag(hashTag, memberId, board);
    }

}
