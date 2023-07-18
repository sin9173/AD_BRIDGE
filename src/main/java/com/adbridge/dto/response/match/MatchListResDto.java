package com.adbridge.dto.response.match;

import com.adbridge.entity.Match;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchListResDto { //매칭 데이터 리스트 조회 응답 데이터

    private Long id;

    /** 회원 정보 */
    private String username; //회원 아이디

    private String email; // 회원 이메일

    /** 매칭데이터 */
    private String purpose; // 제작목적

    private String style; //연출스타일

    /** 제작 기간 */
    private String make_count; //제작편수

    private String video_length; //영상길이

    private String hope_delivery_date; //희망 납품일자


    /** 상세정보 */
    private String video_title; //영상제목

    private String company; // 업체명

    private String video_link; //참고영상링크
    
    private String budget; //예산범위

    private String content; //상세제작내용

    @QueryProjection
    public MatchListResDto(Match match) {
        this.id = match.getId();
        this.username = match.getMember().getUsername();
        this.email = match.getMember().getUsername();
        this.purpose = match.getPurpose();
        this.style = match.getStyle();
        this.make_count = match.getMakeCount();
        this.video_length = match.getVideoLength();
        this.hope_delivery_date = match.getHopeDeliveryDate();
        this.video_title = match.getVideoTitle();
        this.company = match.getCompany();
        this.video_link = match.getVideoLink();
        this.budget = match.getBudget();
        this.content = match.getContent();
    }

}
