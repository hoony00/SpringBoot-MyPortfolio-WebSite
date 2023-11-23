package com.hoon.hoonportfolio.Repository;


import com.hoon.hoonportfolio.Domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 *     유저 관련 기능을 담당하는 Repository
 *
 *   @version          1.00    2023.02.28
 *   @author           이상훈
 */

public interface UserRepository extends JpaRepository<UserEntity, String> {


    @Query("SELECT m FROM UserEntity m WHERE m.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

  //  Optional<UserEntity> findByEmail(String email);

}
