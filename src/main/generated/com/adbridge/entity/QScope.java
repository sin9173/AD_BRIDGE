package com.adbridge.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScope is a Querydsl query type for Scope
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScope extends EntityPathBase<Scope> {

    private static final long serialVersionUID = -391840514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScope scope = new QScope("scope");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatch match;

    public final StringPath name = createString("name");

    public QScope(String variable) {
        this(Scope.class, forVariable(variable), INITS);
    }

    public QScope(Path<? extends Scope> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScope(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScope(PathMetadata metadata, PathInits inits) {
        this(Scope.class, metadata, inits);
    }

    public QScope(Class<? extends Scope> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new QMatch(forProperty("match"), inits.get("match")) : null;
    }

}

