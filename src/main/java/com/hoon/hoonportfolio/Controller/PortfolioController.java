package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j // 로그를 위한 어노테이션
public class PortfolioController {
    private final UserService userService;

    @Autowired
    public PortfolioController(UserService userService) {
        this.userService = userService;
    }











}
