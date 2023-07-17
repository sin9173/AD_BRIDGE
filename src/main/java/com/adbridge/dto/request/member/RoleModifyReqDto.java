package com.adbridge.dto.request.member;

import com.adbridge.enums.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleModifyReqDto { //권한 수정 요청데이터
    
    private Long member_id; //회원 인덱스
    
    private MemberRole role; //권한
}
