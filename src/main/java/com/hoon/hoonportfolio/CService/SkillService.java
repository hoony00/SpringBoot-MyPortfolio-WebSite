package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.Domain.QSkill;
import com.hoon.hoonportfolio.Domain.Skill;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hoon.hoonportfolio.Domain.QSkill.skill;
import static com.hoon.hoonportfolio.Domain.QUserEntity.userEntity;

/**
 * 기술스택 관련 기능을 담당하는 Service
 *
 * @author
 * @version 1.00    2023.10.14
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
@RequiredArgsConstructor
public class SkillService {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Autowired
    public void setQueryFactory(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveSkill(String email) {
        queryFactory = new JPAQueryFactory(em);

        Optional<UserEntity> user = Optional.ofNullable(queryFactory
                .selectFrom(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne());

        for (int i = 0; i < 3; i++) {
            Skill career = Skill.builder()
                    .user(user.get())
                    .skillName("new")
                    .build();

            em.merge(career);
        }
    }

    //email로 기술스택 조회
    public List<String> findSkillByEmail(String email) {
        queryFactory = new JPAQueryFactory(em);
        List<Skill> skillList = queryFactory
                .selectFrom(skill)
                .where(skill.user.email.eq(email))
                .fetch();

        //  List<Skill> skillList = skillRepository.findAllByUserEmail(email);
        List<String> skillNameList = new ArrayList<>();
        for (Skill skill : skillList) {
            skillNameList.add(String.valueOf(skill.getSid()));
        }
        for (Skill skill : skillList) {
            skillNameList.add(String.valueOf(skill.getSkillName()));
        }
        return skillNameList;
    }

    // sid와 skillName으로 skill 업데이트
    public void updateSkill(String sid, String skillName) {

        QSkill qSkill = QSkill.skill;

        Skill skill = queryFactory
                .selectFrom(qSkill)
                .where(qSkill.sid.eq(Long.valueOf(sid)))
                .fetchOne();

        if (skill != null) {
            skill.setSkillName(skillName);

            // 엔터티 수정
            em.merge(skill);

        }

    }
}
