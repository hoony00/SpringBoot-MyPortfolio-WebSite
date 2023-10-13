package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Portfolio;
import com.hoon.hoonportfolio.Domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *    Project 기능을 담당하는 Portfolio
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
