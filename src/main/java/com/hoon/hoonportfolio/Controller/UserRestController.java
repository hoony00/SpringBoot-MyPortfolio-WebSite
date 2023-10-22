package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.UserService;
import com.hoon.hoonportfolio.DTO.ExplanationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j // 로그를 위한 어노테이션
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }



    //유저 자기소개 업데이트
    @PostMapping("user/updateExplanation")
    public ResponseEntity<Map<String, String>> updateExplanation(@RequestBody ExplanationRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println("업데이트 시작 email: " + request.getEmail());
            String email = request.getEmail();
            String explanation = request.getExplanation();

            // 이메일을 사용하여 사용자의 자기소개 업데이트
            userService.updateExplanation(email, explanation);

            response.put("result", "success");
        } catch (Exception e) {
            response.put("result", "error");
        }

        return ResponseEntity.ok(response);
    }

}
