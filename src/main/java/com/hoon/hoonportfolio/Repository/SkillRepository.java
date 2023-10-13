package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Career;
import com.hoon.hoonportfolio.Domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *    Skill 기능을 담당하는 Repository
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
