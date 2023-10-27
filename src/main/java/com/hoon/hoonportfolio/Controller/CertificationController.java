package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.CertificationService;
import com.hoon.hoonportfolio.CService.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j // 로그를 위한 어노테이션
@RequiredArgsConstructor
public class CertificationController {

    @Autowired
    private final CertificationService certificationService;

    //조회하기
    @GetMapping("/certification/select")
    public ResponseEntity<List<String>> selectSkill(String email){
        List<String> certification = certificationService.findCertificationByEmail(email);
        if(certification.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            int maxCount = Math.min(3, certification.size());
            List<String> selectedCertifications = certification.subList(0, maxCount);
        return ResponseEntity.ok(selectedCertifications);
        }

    }












}