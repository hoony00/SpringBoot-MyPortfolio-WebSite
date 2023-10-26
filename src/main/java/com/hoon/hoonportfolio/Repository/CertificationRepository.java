package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Certification;
import com.hoon.hoonportfolio.Domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *    경력 기능을 담당하는 Repository
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findAllByUserEmail(String email);


}
