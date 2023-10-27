package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.Domain.Certification;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.CertificationRepository;
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
    @Autowired
    private final CertificationRepository certificationRepository;

    @Autowired
    private final UserRepository userRepository;



    public void saveCertification(String email) {
       Optional<UserEntity> user = userRepository.findByEmail(email);

       //자격증 저장 3번 반복
        for(int i=0; i<3; i++){
            Certification career = Certification.builder()
                    .user(user.get())
                    .cerName(" ")
                    .build();
            certificationRepository.save(career);
        }

    }

    //email로 certificate 조회
    public List<String> findCertificationByEmail(String email) {
        List<Certification> cerficationList = certificationRepository.findAllByUserEmail(email);
        List<String> CerNameList = new ArrayList<>();
        for (Certification certification : cerficationList) {
            //빈 문자열이나 Null 값이 들어오면 추가하지않음
            if(certification.getCerName().equals(" ") || certification.getCerName().equals("")){
                continue;
            }
            CerNameList.add(certification.getCerName()+certification.getCerid());
        }

        return CerNameList;

    }




}
