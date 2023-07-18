package com.adbridge.dto.request.match;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchSearchReqDto { //매칭데이터 검색 요청 데이터

    private String username; //회원 아이디
    
    private String email; // 회원 이메일

    private String purpose; //제작목적

    private String style; //연출스타일

    private String video_title; //영상제목

    private String company; //업체명

}
