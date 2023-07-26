package com.adbridge.dto.response.member;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.adbridge.dto.response.member.QMemberListResDto is a Querydsl Projection type for MemberListResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberListResDto extends ConstructorExpression<MemberListResDto> {

    private static final long serialVersionUID = -1059258462L;

    public QMemberListResDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<com.adbridge.enums.MemberRole> role) {
        super(MemberListResDto.class, new Class<?>[]{long.class, String.class, String.class, com.adbridge.enums.MemberRole.class}, id, username, email, role);
    }

}

