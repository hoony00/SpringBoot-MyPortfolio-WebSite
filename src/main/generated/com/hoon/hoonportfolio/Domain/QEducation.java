package com.hoon.hoonportfolio.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEducation is a Querydsl query type for Education
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEducation extends EntityPathBase<Education> {

    private static final long serialVersionUID = 945074277L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEducation education = new QEducation("education");

    public final NumberPath<Long> eid = createNumber("eid", Long.class);

    public final StringPath name = createString("name");

    public final QUserEntity user;

    public QEducation(String variable) {
        this(Education.class, forVariable(variable), INITS);
    }

    public QEducation(Path<? extends Education> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEducation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEducation(PathMetadata metadata, PathInits inits) {
        this(Education.class, metadata, inits);
    }

    public QEducation(Class<? extends Education> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

