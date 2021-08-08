package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BoardLike> boardLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BoardHashTag> hashTagList = new ArrayList<>();

    private Long seriesId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long memberId;

    @Column(nullable = false)
    private Boolean isPrivate;

    private int likeCount;

    private String boardThumbnailUrl;

    @Builder
    public Board(Long seriesId, String title, String content, Long memberId, Boolean isPrivate, String boardThumbnailUrl) {
        this.seriesId = seriesId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.isPrivate = isPrivate;
        this.likeCount = 0;
        this.boardThumbnailUrl = boardThumbnailUrl;
    }

    public void addHashTag(List<String> hashTagList, Long memberId) {
        List<BoardHashTag> boardHashTagList = hashTagList.stream().map(boardHashTag -> BoardHashTag.of(boardHashTag, memberId, this)).collect(Collectors.toList());
        this.hashTagList.addAll(boardHashTagList);
    }

}
