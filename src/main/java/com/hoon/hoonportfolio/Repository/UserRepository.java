package com.hoon.hoonportfolio.Repository;


import com.hoon.hoonportfolio.Domain.Project;
import com.hoon.hoonportfolio.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *     유저 관련 기능을 담당하는 Repository
 *
 *   @version          1.00    2023.02.28
 *   @author           이상훈
 */

public interface UserRepository extends JpaRepository<User, String> {



    Optional<User> findByEmail(String email);

}
