package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.UserService;
import com.hoon.hoonportfolio.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // 로그를 위한 어노테이션
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @PostMapping("/user/joinAction") // 회원가입 액션 처리
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        log.info("회원가입 폼에서 전달받은 데이터 : " + userDTO.toString());
        if (bindingResult.hasErrors()) {
            // 폼 유효성 검사 오류가 있는 경우 회원가입 폼으로 다시 이동
            log.info("회원가입 폼에 유효성 검사 오류가 있습니다.");
            return "user/join";
        }
        try {
            userService.registerNewUser(userDTO);
            return "redirect:/user/joinSuccess"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (IllegalStateException e) {
            // 회원가입 중 오류 발생 시 오류 메시지와 함께 회원가입 폼으로 다시 이동
            model.addAttribute("errorMessage", e.getMessage());
            return "user/join";
        }
    }

    //
    @GetMapping("/user/joinSuccess")
    public String joinSuccess() {

        return "alert/suc";
    }

    // 로그아웃
    @GetMapping("/user/logout")
    public String logout() {

        return "redirect:/";
    }


}
