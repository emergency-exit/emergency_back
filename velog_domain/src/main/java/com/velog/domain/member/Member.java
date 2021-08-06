package com.velog.domain.member;

import com.velog.domain.BaseTimeEntity;
import com.velog.domain.board.Series;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false, length = 50)
    private String name;

    private String memberImage;

    private String velogName;

    private String description;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Series> seriesList = new ArrayList<>();

    @Builder
    public Member(Email email, Password password, String name, String memberImage, String velogName, String description) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberImage = memberImage;
        this.velogName = velogName;
        this.description = description;
    }

    public void updateInfo(String description, String name, String velogName) {
        this.description = description;
        this.name = name;
        this.velogName = velogName;
    }

    public Series addSeries(String seriesName) {
        Series series = Series.of(this, seriesName);
        this.seriesList.add(series);
        return series;
    }

}
