package com.adbridge.dto.response.match;

import com.adbridge.dto.request.match.MatchSaveReqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MatchDetailResDto {

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

    private String content; //상세제작내용

    private List<ScopeListResDto> scope_list = new ArrayList<>(); //제작범위 리스트

    @Getter
    @Setter
    @ToString
    class ScopeListResDto {
        private String name;
    }
}
