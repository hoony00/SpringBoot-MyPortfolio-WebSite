package com.hoon.hoonportfolio.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = 2038341398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final StringPath description = createString("description");

    public final StringPath githubLink = createString("githubLink");

    public final StringPath imageUrl = createString("imageUrl");

    public final ArrayPath<byte[], Byte> mainImage = createArray("mainImage", byte[].class);

    public final ListPath<Photo, QPhoto> photos = this.<Photo, QPhoto>createList("photos", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final NumberPath<Long> proid = createNumber("proid", Long.class);

    public final StringPath title = createString("title");

    public final QUserEntity user;

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

