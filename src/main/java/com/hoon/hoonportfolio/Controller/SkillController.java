package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j // 로그를 위한 어노테이션
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    //조회하기
    @GetMapping("/skill/select")
    public ResponseEntity<List<String>> selectCertifications(String email) {
        List<String> skillList = skillService.findSkillByEmail(email);
            return ResponseEntity.ok(skillList);
    }













}
