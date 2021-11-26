package com.velog.domain.member;

import com.velog.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminMember extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String memberImage;

    public AdminMember(String email, String password, String memberImage) {
        this.email = email;
        this.password = password;
        this.memberImage = memberImage;
    }

}
