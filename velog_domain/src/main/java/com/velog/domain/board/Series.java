package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import com.velog.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Series extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String seriesName;

    public Series(Member member, String seriesName) {
        this.member = member;
        this.seriesName = seriesName;
    }

    public static Series of(Member member, String seriesName) {
        return new Series(member, seriesName);
    }

}
