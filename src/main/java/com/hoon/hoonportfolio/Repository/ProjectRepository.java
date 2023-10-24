package com.hoon.hoonportfolio.Repository;

import com.hoon.hoonportfolio.Domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *    Project 기능을 담당하는 Repository
 *
 *   @version          1.00    
 *   @author           이상훈
 */

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUserEmail(String email);

}
