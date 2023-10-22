package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *    경력 기능을 담당하는 Repository
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface CertificationRepository extends JpaRepository<Certification, Long> {

}
