package com.velog.domain;

import com.velog.domain.member.Email;
import com.velog.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {

    @Test
    void 정상적인_이메일() {
        // given
        String email = "tnswh2023@gmail.com";

        // when
        Email emailResult = Email.of(email);

        // then
        assertThat(emailResult.getEmail()).isEqualTo(email);
    }

    @Test
    void 아이디_부분이_없을_경우() {
        // given
        String email = "@gmail.com";

        assertThatThrownBy(
            () -> Email.of(email)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 호스트_부분이_없을_경우() {
        // given
        String email = "tnswh2023@";

        assertThatThrownBy(
                () -> Email.of(email)
        ).isInstanceOf(ValidationException.class);
    }

}
