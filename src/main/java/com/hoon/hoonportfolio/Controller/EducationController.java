package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.EducationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j // 로그를 위한 어노테이션
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    //조회하기
    @GetMapping("/education/select")
    public ResponseEntity<List<String>> selectSkill(String email){
        List<String> educationList = educationService.findEducationByEmail(email);

        return ResponseEntity.ok(educationList);


    }

}
