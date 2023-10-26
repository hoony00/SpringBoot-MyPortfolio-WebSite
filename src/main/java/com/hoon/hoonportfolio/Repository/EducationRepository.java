package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Education;
import com.hoon.hoonportfolio.Domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *    자격증 기능을 담당하는 Repository
 *
 *   @version          1.00
 *   @author           이상훈
 */

public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findAllByUserEmail(String email);


}
