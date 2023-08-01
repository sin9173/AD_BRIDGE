package com.adbridge.entity;

import com.adbridge.dto.request.match.MatchModifyReqDto;
import com.adbridge.dto.request.match.MatchSaveReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Matching extends BaseEntity { //매칭데이터

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String purpose = ""; //제작목적

    private String style = ""; //연출스타일

    /** 제작 기간 */
    private String makeCount = ""; //제작편수

    private String videoLength = ""; //영상길이

    private String hopeDeliveryDate = ""; //희망 납품 일자

    /** 상세 정보 */
    private String videoTitle = ""; //영상제목
    private String company = ""; //업체명
    private String videoLink = ""; //참고영상링크
    private String budget = ""; //예산범위
    private String content = ""; //상세제작내용

    @ColumnDefault("0")
    @Column(columnDefinition = "TINYINT")
    private Boolean checkYn = false;

    @OneToMany(mappedBy = "matching")
    private List<Scope> scopeList = new ArrayList<>(); //제작 범위

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Matching(MatchSaveReqDto dto, Member member) {
        this.purpose = dto.getPurpose();
        this.style = dto.getStyle();
        this.makeCount = dto.getMake_count();
        this.videoLength = dto.getVideo_length();
        this.hopeDeliveryDate = dto.getHope_delivery_date();
        this.videoTitle = dto.getVideo_title();
        this.company = dto.getCompany();
        this.videoLink = dto.getVideo_link();
        this.budget = dto.getBudget();
        this.content = dto.getContent();

        this.member = member;
    }

    public void updateInfo(MatchModifyReqDto dto) {
        if(dto.getPurpose()!=null) this.purpose = dto.getPurpose();
        if(dto.getStyle()!=null) this.style = dto.getStyle();
        if(dto.getMake_count()!=null) this.makeCount = dto.getMake_count();
        if(dto.getVideo_length()!=null) this.videoLength = dto.getVideo_length();
        if(dto.getHope_delivery_date()!=null) this.hopeDeliveryDate = dto.getHope_delivery_date();
        if(dto.getVideo_title()!=null) this.videoTitle = dto.getVideo_title();
        if(dto.getCompany()!=null) this.company = dto.getCompany();
        if(dto.getVideo_link()!=null) this.videoLink = dto.getVideo_link();
        if(dto.getBudget()!=null) this.budget = dto.getBudget();
        if(dto.getContent()!=null) this.content = dto.getContent();

        this.scopeList.clear();

        dto.getScope_list().forEach((scope) -> addScope(new Scope(scope.getName())));
    }

    public void addScope(Scope scope) {
        this.scopeList.add(scope);
        scope.updateMatch(this);
    }

    public void updateCheckYn(Boolean checkYn) {
        this.checkYn = checkYn;
    }
}
