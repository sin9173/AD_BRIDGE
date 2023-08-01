package com.adbridge.dto.request.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpReqDto { // 회원가입 요청 데이터

    private String username; //회원 아이디

    private String password; //비밀번호

    private String email; //이메일

    private String name; //이름

    private String phone; // 전화번호

}
