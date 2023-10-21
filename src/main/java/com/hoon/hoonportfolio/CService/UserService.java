package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.DTO.UserDTO;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 유저 관련 기능을 담당하는 Service
 *
 * @author
 * @version 1.00    2023.10.14
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public byte[] getUserPhoto(String eamil) {
        Optional<UserEntity> userOptional = userRepository.findById(userRepository.findByEmail(eamil).get().getUid());
        // 사용자를 찾지 못한 경우 null 또는 기본 이미지 반환
        return userOptional.map(UserEntity::getMyPhoto).orElse(null);
    }


    public void registerNewUser(UserDTO userDTO) throws IllegalStateException {
        if (userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty() || userDTO.getPwcheck().isEmpty() || userDTO.getExplanation().isEmpty()) {
            throw new IllegalStateException("모든 항목을 입력해주세요.");
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

/*        if (!userDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalStateException("이메일 형식에 맞게 입력해주세요.");
        }

        if (userDTO.getExplanation().length() < 10 || userDTO.getExplanation().length() > 30) {
            throw new IllegalStateException("간단 자기소개를 10글자 이상, 30글자 이하로 입력해주세요.");
        }

        if (!userDTO.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$")) {
            throw new IllegalStateException("비밀번호는 6글자 이상의 영어+숫자 조합으로 입력해주세요.");
        }

        if (!userDTO.getPassword().equals(userDTO.getPwcheck())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }*/

            UserEntity userEntity = UserEntity.builder()
                    .name(userDTO.getName())
                    .email(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .explanation(userDTO.getExplanation())
                    .build();

            userRepository.save(userEntity);

            log.info("회원가입 완료");


    }


}
