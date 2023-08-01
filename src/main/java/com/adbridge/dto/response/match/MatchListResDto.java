package com.adbridge.dto.response.match;

import com.adbridge.entity.Matching;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class MatchListResDto { //매칭 데이터 리스트 조회 응답 데이터

    private Long id;

    /** 회원 정보 */
    private String username; //회원 아이디

    private String email; // 회원 이메일
    
    private String member_name; //회원명
    
    private String phone; //전화번호

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

    private Boolean check_yn; //확인여부

    private String created_at; //날짜

    @QueryProjection
    public MatchListResDto(Matching matching) {
        this.id = matching.getId();
        this.username = matching.getMember().getUsername();
        this.email = matching.getMember().getUsername();
        this.member_name = matching.getMember().getName();
        this.phone = matching.getMember().getPhone();

        this.purpose = matching.getPurpose();
        this.style = matching.getStyle();
        this.make_count = matching.getMakeCount();
        this.video_length = matching.getVideoLength();
        this.hope_delivery_date = matching.getHopeDeliveryDate();
        this.video_title = matching.getVideoTitle();
        this.company = matching.getCompany();
        this.video_link = matching.getVideoLink();
        this.budget = matching.getBudget();
        this.content = matching.getContent();
        this.check_yn = matching.getCheckYn();

        this.created_at = matching.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm"));
    }

}
