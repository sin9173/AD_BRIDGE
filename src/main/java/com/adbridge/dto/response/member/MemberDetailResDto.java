package com.adbridge.dto.response.member;

import com.adbridge.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDetailResDto { //회원 상세 조회 응답 데이터

    private Long id; //인덱스

    private String username; //회원 아이디

    private String email; //이메일
    
    private String name; //회원명
    
    private String phone; //전화번호

    private String role_code; //권한코드

    private String role_name; //권한명

    public MemberDetailResDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.role_code = member.getRole().getCode();
        this.role_name = member.getRole().getDesc();
    }

}
