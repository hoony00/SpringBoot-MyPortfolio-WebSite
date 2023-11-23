package com.hoon.hoonportfolio.Domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 841893489L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final ListPath<Education, QEducation> careers = this.<Education, QEducation>createList("careers", Education.class, QEducation.class, PathInits.DIRECT2);

    public final ListPath<Certification, QCertification> certifications = this.<Certification, QCertification>createList("certifications", Certification.class, QCertification.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath explanation = createString("explanation");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ArrayPath<byte[], Byte> profileImage = createArray("profileImage", byte[].class);

    public final ListPath<Project, QProject> projects = this.<Project, QProject>createList("projects", Project.class, QProject.class, PathInits.DIRECT2);

    public final EnumPath<com.hoon.hoonportfolio.constant.Role> role = createEnum("role", com.hoon.hoonportfolio.constant.Role.class);

    public final ListPath<Skill, QSkill> skills = this.<Skill, QSkill>createList("skills", Skill.class, QSkill.class, PathInits.DIRECT2);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

