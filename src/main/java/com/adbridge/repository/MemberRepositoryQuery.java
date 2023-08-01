package com.adbridge.repository;

import com.adbridge.dto.request.member.MemberSearchReqDto;
import com.adbridge.dto.response.member.MemberListResDto;
import com.adbridge.dto.response.member.QMemberListResDto;
import com.adbridge.enums.MemberRole;
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

import static com.adbridge.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public Page<MemberListResDto> memberSearch(MemberSearchReqDto dto, Pageable pageable) {
        List<MemberListResDto> content = queryFactory
                .select(new QMemberListResDto(member))
                .from(member)
                .where(
                        usernameContains(dto.getUsername()),
                        emailContains(dto.getEmail()),
                        roleEq(dto.getRole())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        usernameContains(dto.getUsername()),
                        emailContains(dto.getEmail()),
                        roleEq(dto.getRole())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression usernameContains(String username) {
        return StringUtils.hasText(username) ? member.username.contains(username) : null;
    }

    private BooleanExpression emailContains(String email) {
        return StringUtils.hasText(email) ? member.email.contains(email) : null;
    }

    private BooleanExpression roleEq(MemberRole role) {
        return role != null ? member.role.eq(role) : null;
    }
}


