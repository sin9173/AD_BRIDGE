package com.adbridge.dto.response.member;

import com.adbridge.enums.MemberRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberListResDto {

    private Long id; //인덱스

    private String username; //회원아이디

    private String email; //이메일

    private String role_code; //권한 코드

    private String role_name; //권한명


    @QueryProjection
    public MemberListResDto(Long id, String username, String email, MemberRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role_code = role.getCode();
        this.role_name = role.getDesc();
    }
}
