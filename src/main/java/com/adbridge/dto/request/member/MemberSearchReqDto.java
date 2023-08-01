package com.adbridge.dto.request.member;

import com.adbridge.enums.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSearchReqDto { //회원 리스트 검색 요청 데이터

    private String username; //회원아이디

    private String email; //이메일
    
    private String name; //이름
    
    private String phone; // 전화번호

    private MemberRole role; //권한
}
