package com.adbridge.repository;

import com.adbridge.dto.request.match.MatchSearchReqDto;
import com.adbridge.dto.response.match.MatchListResDto;
import com.adbridge.dto.response.match.QMatchListResDto;
import com.adbridge.dto.response.member.QMemberListResDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.adbridge.entity.QMatch.match;

@Repository
@RequiredArgsConstructor
public class MatchRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public Page<MatchListResDto> matchSearch(MatchSearchReqDto dto, Pageable pageable) {
        List<MatchListResDto> content = queryFactory
                .select(new QMatchListResDto(match))
                .from(match)
                .where(
                        usernameContains(dto.getUsername()),
                        emailContains(dto.getEmail()),
                        purposeContains(dto.getPurpose()),
                        styleContains(dto.getStyle()),
                        videoTitleContains(dto.getVideo_title()),
                        companyContains(dto.getCompany())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(match.count())
                .from(match)
                .where(
                        usernameContains(dto.getUsername()),
                        emailContains(dto.getEmail()),
                        purposeContains(dto.getPurpose()),
                        styleContains(dto.getStyle()),
                        videoTitleContains(dto.getVideo_title()),
                        companyContains(dto.getCompany())
                );
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression usernameContains(String username) {
        return StringUtils.hasText(username) ? match.member.username.contains(username) : null;
    }

    private BooleanExpression emailContains(String email) {
        return StringUtils.hasText(email) ? match.member.email.contains(email) : null;
    }

    private BooleanExpression purposeContains(String purpose) {
        return StringUtils.hasText(purpose) ? match.purpose.contains(purpose) : null;
    }

    private BooleanExpression styleContains(String style) {
        return StringUtils.hasText(style) ? match.style.contains(style) : null;
    }

    private BooleanExpression videoTitleContains(String videoTitle) {
        return StringUtils.hasText(videoTitle) ? match.videoTitle.contains(videoTitle) : null;
    }

    private BooleanExpression companyContains(String company) {
        return StringUtils.hasText(company) ? match.company.contains(company) : null;
    }
}
