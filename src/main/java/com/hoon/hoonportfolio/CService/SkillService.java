package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.Domain.Skill;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.SkillRepository;
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
    private final SkillRepository skillRepository;

    @Autowired
    private final UserRepository userRepository;



    public void saveSkill(String email) {
       Optional<UserEntity> user = userRepository.findByEmail(email);

       //자격증 저장 3번 반복
        for(int i=0; i<3; i++){
            Skill career = Skill.builder()
                    .user(user.get())
                    .skillName("new")
                    .build();
            skillRepository.save(career);
        }

    }

    //email로 기술스택 조회
    public List<String> findSkillByEmail(String email) {
        List<Skill> skillList = skillRepository.findAllByUserEmail(email);
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
        Optional<Skill> skill = skillRepository.findById(Long.valueOf(sid));
        System.out.println("서비스에서 skill.get().getSkillName() = " + skill.get().getSkillName());
        skill.get().setSkillName(skillName);
        skillRepository.save(skill.get());
    }




}
