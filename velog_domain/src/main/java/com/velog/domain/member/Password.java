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
public class Password {

    private final static String exp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

    private String password;

    public Password(String password) {
        this.password = password;
    }

    public static Password validationPassword(String password) {
        validatePassword(password);
        return new Password(password);
    }

    public static Password of(String encodedPassword) {
        return new Password(encodedPassword);
    }

    private static void validatePassword(String password) {
        if (!Pattern.matches(exp, password)) {
            throw new ValidationException("비밀번호 형식이 맞지 않습니다.");
        }
    }

}
