package com.velog.domain.member;

import com.velog.exception.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Embeddable
public class Email {

    private final static String exp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$*";

    @Column(nullable = false)
    private String email;

    public Email(String email) {
        this.validateEmail(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validateEmail(String email) {
        if (!Pattern.matches(exp, email)) {
            throw new ValidationException("이메일 형식이 맞지 않습니다.");
        }
    }

}
