package com.adbridge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatch is a Querydsl query type for Match
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatch extends EntityPathBase<Match> {

    private static final long serialVersionUID = -397436817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatch match = new QMatch("match");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath budget = createString("budget");

    public final BooleanPath checkYn = createBoolean("checkYn");

    public final StringPath company = createString("company");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final BooleanPath deleteYn = _super.deleteYn;

    public final StringPath hopeDeliveryDate = createString("hopeDeliveryDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath makeCount = createString("makeCount");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath purpose = createString("purpose");

    public final ListPath<Scope, QScope> scopeList = this.<Scope, QScope>createList("scopeList", Scope.class, QScope.class, PathInits.DIRECT2);

    public final StringPath style = createString("style");

    public final StringPath videoLength = createString("videoLength");

    public final StringPath videoLink = createString("videoLink");

    public final StringPath videoTitle = createString("videoTitle");

    public QMatch(String variable) {
        this(Match.class, forVariable(variable), INITS);
    }

    public QMatch(Path<? extends Match> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatch(PathMetadata metadata, PathInits inits) {
        this(Match.class, metadata, inits);
    }

    public QMatch(Class<? extends Match> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

