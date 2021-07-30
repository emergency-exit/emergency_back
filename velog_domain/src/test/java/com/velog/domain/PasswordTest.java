package com.velog.domain;

import com.velog.domain.member.Password;
import com.velog.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @Test
    void 정상적인_비밀번호() {
        //'숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 16자'까지 허용
        // given
        String password = "tnswh2023@";

        // when
        Password resultPassword = Password.validationPassword(password);

        // then
        assertThat(resultPassword.getPassword()).isEqualTo(password);
    }

    @Test
    void 여덟자리가_안될_경우() {
        // given
        String password = "tnswh2@";

        assertThatThrownBy(
            () -> Password.validationPassword(password)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 열여섯자리보다_길_경우() {
        // given
        String password = "tnswh22342352434346346345345345345@";

        assertThatThrownBy(
                () -> Password.validationPassword(password)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 특수문자가_없을_경우() {
        // given
        String password = "tnswh2023SSDF";

        assertThatThrownBy(
                () -> Password.validationPassword(password)
        ).isInstanceOf(ValidationException.class);
    }

}
