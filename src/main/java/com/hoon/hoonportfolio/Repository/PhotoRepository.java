package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *    Project 기능을 담당하는 Repository
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
