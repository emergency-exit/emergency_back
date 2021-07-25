package com.velog.domain.member;

import com.velog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    private String memberImage;

    @Builder
    public Member(String email, String password, String name, String memberImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberImage = memberImage;
    }

}
