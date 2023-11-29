package com.hoon.hoonportfolio.CService;

import com.hoon.hoonportfolio.DTO.UserDTO;
import com.hoon.hoonportfolio.DTO.UserInfoDTO;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.UserRepository;
import com.hoon.hoonportfolio.constant.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 유저 관련 기능을 담당하는 Service
 *
 * @author
 * @version 1.00    2023.10.14
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService  {

    private final UserRepository userRepository;


    public List<UserInfoDTO> getAllUsersInfo(){
        List<UserEntity> userInfoDTOList = userRepository.findAll();
        return userInfoDTOList.stream()
                .map(userEntity -> new UserInfoDTO(userEntity.getName(),
                        userEntity.getExplanation(),
                        userEntity.getEmail()))
                .collect(Collectors.toList());
    }


    //이메일 존재확인
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    //updateExplanation
    public void updateExplanation(String email, String explanation) {
        // 사용자를 찾아서 자기소개 업데이트
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setExplanation(explanation);
            userRepository.save(user);
        }else{
            // 사용자를 찾지 못한 경우 예외 발생
            throw new IllegalStateException("회원 정보를 찾을 수 없습니다.");
        }
    }

    // 이름과 자기소개 가져오기
    public UserDTO getNameAndExplanation(String email) {
        System.out.println("getNameAndExplanation 이메일: " + email);
        if(userRepository.findByEmail(email).isPresent()){
            Optional<UserEntity> user = userRepository.findById(email);
            return UserDTO.builder()
                    .name(user.get().getName())
                    .explanation(user.get().getExplanation())
                    .email(email)
                    .build();
        }else{
            // 사용자를 찾지 못한 경우 예외 발생
            if(email == null){
                throw new IllegalStateException("이메일을 입력해주세요.");
            }else{
                throw new IllegalStateException("UserService getNameAndExplantion) : "+ email + "은 존재하지 않는 이메일입니다.");
            }
        }

    }

    public UserDTO getSelect(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            Optional<UserEntity> user = userRepository.findById(email);
            return UserDTO.builder()
                    .name(user.get().getName())
                    .explanation(user.get().getExplanation())
                    .email(email)
                    .build();
        }else{
            // 사용자를 찾지 못한 경우 예외 발생
            if(email == null){
                throw new IllegalStateException("이메일을 입력해주세요.");
            }else{
                throw new IllegalStateException(email + "은 존재하지 않는 이메일입니다.");
            }
        }

    }

    // 프로필 사진 저장
    public void saveProfileImage(String email, byte[] imageBytes) {
        Optional<UserEntity> userOptional = userRepository.findById(userRepository.findById(email).get().getEmail());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setProfileImage(imageBytes);
            userRepository.save(user);
        }else{
            // 사용자를 찾지 못한 경우 예외 발생
            throw new IllegalStateException("회원 정보를 찾을 수 없습니다.");
        }
    }

    public byte[] getProfileImage(String email) {
        System.out.println("getProfileImage 이메일: " + email);
        Optional<UserEntity> userOptional = userRepository.findById(userRepository.findByEmail(email).get().getEmail());
        // 사용자를 찾지 못한 경우 null 또는 기본 이미지 반환
        return userOptional.map(UserEntity::getProfileImage).orElse(null);
    }


    public void registerNewUser(UserDTO userDTO, PasswordEncoder passwordEncoder) throws IllegalStateException {
        if (userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty() || userDTO.getPwcheck().isEmpty() || userDTO.getExplanation().isEmpty()) {
            throw new IllegalStateException("모든 항목을 입력해주세요.");
        }

      /*  if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        if (!userDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
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

        UserEntity user = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .explanation(userDTO.getExplanation())
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
        log.info("=============================");
        log.info("현재 로그인 시도 사용자 --->" + member);
        log.info("=============================");

        return User.builder()
                .username(member.getEmail())
                .roles("USER")
                // 스프링 시큐리티에서 암호를 넣을 땐 꼭 암호화를 넣어야 함 -> 사용자가 입력한 비밀번호를 자동으로 암호화해서 비교함
                .password(member.getPassword())
                .build();
    }


}
