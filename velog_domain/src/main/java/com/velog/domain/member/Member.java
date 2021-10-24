package com.velog.domain.member;

import com.velog.domain.BaseTimeEntity;
import com.velog.domain.board.Series;
import com.velog.enumData.ProviderType;
import com.velog.exception.ValidationException;
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

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Series> seriesList = new ArrayList<>();

    @Builder
    public Member(Email email, Password password, String name, String memberImage, String velogName, String description, ProviderType provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberImage = memberImage;
        this.velogName = velogName;
        this.description = description;
        this.provider = provider;
    }

    public void updateInfo(String description, String name, String velogName) {
        this.description = description;
        this.name = name;
        this.velogName = velogName;
    }

    public void addSeries(String seriesName) {
        if (this.findSeriesByName(seriesName)) {
            throw new ValidationException(String.format("이미 존재하는 시리즈 (%s) 입니다.", seriesName));
        }
        Series series = Series.of(this, seriesName);
        this.seriesList.add(series);
    }

    private boolean findSeriesByName(String seriesName) {
        return seriesList.stream().anyMatch(series -> series.isSameEntity(seriesName));
    }

}
