package com.adbridge.dto.request.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginReqDto { //로그인 요청 데이터

    private String username; //회원 아이디

    private String password; //회원 비밀번호
}
