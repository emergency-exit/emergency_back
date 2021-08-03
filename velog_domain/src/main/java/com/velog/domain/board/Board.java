package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BoardLike> boardLikeList = new ArrayList<>();

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long memberId;

    @Column(nullable = false)
    private Boolean isPrivate;

    private int likeCount;

    public Board(Series series, String title, String content, Long memberId, Boolean isPrivate) {
        this.series = series;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.isPrivate = isPrivate;
        this.likeCount = 0;
    }

}
