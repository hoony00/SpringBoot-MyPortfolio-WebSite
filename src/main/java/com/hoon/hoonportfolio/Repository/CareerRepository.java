package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *    자격증 기능을 담당하는 Repository
 *
 *   @version          1.00
 *   @author           이상훈
 */

public interface CareerRepository extends JpaRepository<Career, Long> {

}
