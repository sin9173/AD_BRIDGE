package com.adbridge.dto.response.auth;

import com.adbridge.dto.auth.AuthDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResDto { //로그인 응답 데이터

    private Long member_id; //회원 인덱스

    private String username; //회원 아이디

    private String email; //이메일

    private String role_code; //권한 코드

    private String role_name; //권한 명

    @Builder
    public LoginResDto(AuthDto dto) {
        this.member_id = dto.getId();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.role_code = dto.getMemberRole().getCode();
        this.role_name = dto.getMemberRole().getDesc();
    }
}
