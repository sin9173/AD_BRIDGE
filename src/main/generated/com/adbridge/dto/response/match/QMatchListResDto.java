package com.adbridge.dto.response.match;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.adbridge.dto.response.match.QMatchListResDto is a Querydsl Projection type for MatchListResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMatchListResDto extends ConstructorExpression<MatchListResDto> {

    private static final long serialVersionUID = -228634736L;

    public QMatchListResDto(com.querydsl.core.types.Expression<? extends com.adbridge.entity.Match> match) {
        super(MatchListResDto.class, new Class<?>[]{com.adbridge.entity.Match.class}, match);
    }

}

