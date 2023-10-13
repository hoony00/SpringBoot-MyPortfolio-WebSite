package com.hoon.hoonportfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j // 로그를 위한 어노테이션
public class UserController {

    @GetMapping("user/login") // http://localhost/user/login
    public String login() {



        return "user/login";
    }

    @GetMapping("/ex2") // http://localhost/ex2
    public String ex2() {

        return "thymeleaf/ex2";
    }




    @GetMapping("/ex4") // http://localhost/ex4
    public String ex4() {

        return "thymeleaf/ex4";
    }




}
