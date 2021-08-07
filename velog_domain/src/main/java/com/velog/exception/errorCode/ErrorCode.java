package com.velog.exception.errorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {

    NOT_FOUND_EXCEPTION(404, "존재하지 않습니다."),
    JWT_UNAUTHORIZED_EXCEPTION(403, "인증애러입니다."),
    CONFLICT_EXCEPTION(409, "이미 존재합니다."),
    VALIDATION_EXCEPTION(400, "잘못된 입력입니다.");

    private final int code;
    private final String message;

}
