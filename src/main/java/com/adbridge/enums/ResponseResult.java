package com.adbridge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseResult {

    SUCCESS("0", "", "성공"),

    /** 401 HTTP STATUS */
    BAD_CREDENTIAL("40101", "", ""),
    /** 403 HTTP STATUS */
    INVALID_TOKEN("40102", "유효하지 않은 토큰","유효하지 않은 토큰입니다."),

    ACCESS_DENIED("40302", "접근권한이 없습니다.", "접근권한이 없습니다."),

    /** 500 HTTP STATUS */
    ERROR("50001", "", "에러");

    private String code; //응답 코드

    private String description; //설명

    private String message; //응답 메세지
}
