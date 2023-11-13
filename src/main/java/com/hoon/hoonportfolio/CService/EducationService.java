package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.Domain.Education;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.EducationRepository;
import com.hoon.hoonportfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 경력 관련 기능을 담당하는 Service
 *
 * @author
 * @version 1.00    2023.10.14
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
@RequiredArgsConstructor
public class EducationService {
    @Autowired
    private final EducationRepository educationRepository;

    @Autowired
    private final UserRepository userRepository;



    public void saveEducation(String email) {
       Optional<UserEntity> user = userRepository.findByEmail(email);

       //자격증 저장 3번 반복
        for(int i=0; i<3; i++){
            Education education = Education.builder()
                    .user(user.get())
                    .name("new")
                    .build();
            educationRepository.save(education);
        }

    }

    //email로 certificate 조회
    public List<String> findEducationByEmail(String email) {
        List<Education> cerficationList = educationRepository.findAllByUserEmail(email);
        List<String> list = new ArrayList<>();
        for (Education education : cerficationList) {
            list.add(String.valueOf(education.getEid()));
        }
        for(Education education : cerficationList) {
            list.add(education.getName());
        }

        return list;

    }

    public void updateEducation(String eid, String eName) {
        Optional<Education> education = educationRepository.findById(Long.valueOf(eid));
        education.get().setName(eName);
        educationRepository.save(education.get());
    }





}
