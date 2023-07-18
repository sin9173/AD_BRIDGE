package com.adbridge.dto.response.match;

import com.adbridge.dto.request.match.MatchSaveReqDto;
import com.adbridge.entity.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MatchDetailResDto { //매칭데이터 상세 조회 응답데이터

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

    private Boolean check_yn;


    private List<ScopeListResDto> scope_list = new ArrayList<>(); //제작범위 리스트

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public class ScopeListResDto {
        private String name;
    }

    public MatchDetailResDto(Match match) {
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
        this.check_yn = match.getCheckYn();

        this.scope_list = match.getScopeList().stream()
                .map((s) -> new ScopeListResDto(s.getName()))
                .collect(Collectors.toList());
    }
}
