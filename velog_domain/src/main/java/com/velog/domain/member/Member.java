package com.velog.domain.member;

import com.velog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
}
