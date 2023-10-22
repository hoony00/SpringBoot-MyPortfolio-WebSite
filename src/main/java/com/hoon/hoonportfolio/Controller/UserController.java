package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.UserService;
import com.hoon.hoonportfolio.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SessionAttributes("userExplanation")
@Controller
@Slf4j // 로그를 위한 어노테이션
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 이름과 자기소개 가져오기
    @GetMapping("/user/getNameAndExplanation/{email}")
    public String getNameAndExplanation(@PathVariable String email, Model model) {
        model.addAttribute("userExplanation", userService.getNameAndExplanation(email));

        return "layout/newPortfolio";
    }


    //프로필 이미지 저장하기
    @PostMapping("/user/uploadProfileImage")
    public void uploadFile(@RequestParam("file") MultipartFile file, @ModelAttribute("userExplanation") String userExplanation, Model model) {
        try {
            // MultipartFile을 바이트 배열로 변환하여 서비스로 전달
            byte[] imageBytes = file.getBytes();
            userService.saveProfileImage("nasi3611@naver.com", imageBytes); // 이메일 값을 적절히 전달

            model.addAttribute("userExplanation", userExplanation);

        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace(); // 오류 처리를 원하는 방식으로 수정
        }
    }


    // 프로필 사진 조회
    @GetMapping("/user/profileImage/{email}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String email, Model model) {
        byte[] image = userService.getProfileImage(email);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 유형에 따라 변경

            // 프로필 사진을 바이트 배열로 변환하여 모델에 추가
            model.addAttribute("userExplanation", userService.getNameAndExplanation(email));


            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/") // http://localhost
    public String index() { // 홈

        return "index";
    }


    @GetMapping("/user/join") // 회원가입 폼을 표시
    public String showRegistrationForm(Model model) {
        // UserDTO 객체를 사용하여 회원가입 폼을 초기화
        model.addAttribute("userDTO", new UserDTO());
        return "user/join";
    }

    @PostMapping("/user/joinSuccess")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        log.info("회원가입 폼에서 전달받은 데이터 : " + userDTO.toString());

        if (bindingResult.hasErrors()) {
            // 폼 유효성 검사 오류가 있는 경우 회원가입 폼으로 다시 이동
            log.info("회원가입 폼에 유효성 검사 오류가 있습니다.");
            return "user/join";
        }
        try {
            userService.registerNewUser(userDTO);
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            model.addAttribute("userExplanation", userDTO.getEmail());
            System.out.println("회원가입 성공 -> 이메일 : " + userDTO.getEmail());
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            return "alert/suc";
        } catch (IllegalStateException e) {
            // 회원가입 중 오류 발생 시 오류 메시지와 함께 회원가입 폼으로 다시 이동
            model.addAttribute("errorMessage", e.getMessage());
            return "user/join";
        }
    }



    // 로그아웃
    @GetMapping("/user/logout")
    public String logout() {

        return "redirect:/";
    }


}
