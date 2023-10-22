package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.UserService;
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
public class PortfolioController {
    private final UserService userService;

    @Autowired
    public PortfolioController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/portfolio/save") // http://localhost
    public String save() { // 홈

        return "layout/newPortfolio"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
    }









}
