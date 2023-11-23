package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.Domain.Certification;
import com.hoon.hoonportfolio.Domain.QCertification;
import com.hoon.hoonportfolio.Domain.Skill;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.CertificationRepository;
import com.hoon.hoonportfolio.Repository.UserRepository;
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

import static com.hoon.hoonportfolio.Domain.QCertification.certification;
import static com.hoon.hoonportfolio.Domain.QSkill.skill;
import static com.hoon.hoonportfolio.Domain.QUserEntity.userEntity;

/**
 * 자격증 관련 기능을 담당하는 Service
 *
 * @author
 * @version 1.00    2023.10.14
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;

    private final UserRepository userRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory ;

    public void saveCertification(String email) {

        queryFactory = new JPAQueryFactory(em);

        Optional<UserEntity> user = Optional.ofNullable(queryFactory
                .selectFrom(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne());       //자격증 저장 3번 반복

        for(int i=0; i<3; i++){
            Certification career = Certification.builder()
                    .user(user.get())
                    .cerName("new")
                    .build();
            certificationRepository.save(career);
        }
    }

    //email로 certificate 조회
    public List<String> findCertificationByEmail(String email) {
        queryFactory = new JPAQueryFactory(em);
        List<Certification> cerficationList = queryFactory
                .selectFrom(certification)
                .where(certification.user.email.eq(email))
                .fetch();


        List<String> CerNameList = new ArrayList<>();
        for (Certification certification : cerficationList) {
            CerNameList.add(String.valueOf(certification.getCerid()));
        }
        for(Certification certification : cerficationList) {
            CerNameList.add(certification.getCerName());
        }
        return CerNameList;
    }

    // cid 와 cname을 받아서 cname을 수정하는 메소드
    public void updateCertification(String cid, String cname) {
        Optional<Certification> certification = certificationRepository.findById(Long.valueOf(cid));
        certification.get().setCerName(cname);
        certificationRepository.save(certification.get());
    }

}
