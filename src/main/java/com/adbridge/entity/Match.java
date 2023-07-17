package com.adbridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Match extends BaseEntity { //매칭데이터

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String purpose; //제작목적

    private String style; //연출스타일

    /** 제작 기간 */
    private String makeCount; //제작편수

    private String videoLength; //영상길이

    private String hopeDeliveryDate; //희망 납품 일자

    /** 상세 정보 */
    private String videoTitle; //영상제목
    private String company; //업체명
    private String videoLink; //참고영상링크
    private String content; //상세제작내용

    @OneToMany(mappedBy = "match")
    private List<Scope> scopeList; //제작 범위

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
