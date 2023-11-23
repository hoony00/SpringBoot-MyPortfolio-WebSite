package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.ProjectService;
import com.hoon.hoonportfolio.DTO.ProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j // 로그를 위한 어노테이션
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    // 이메일을 받아서 프로젝트 리스트 리턴
    @GetMapping("/projects/selectInfo")
    public ResponseEntity<List<ProjectDTO>> getProjectsInfoByEmail(String email) {
        List<ProjectDTO> projects = projectService.getProjectsByEmail(email);
        if (projects.isEmpty()) {
            log.info("프로젝트가 없습니다.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/projects/delete")
    public ResponseEntity<String> deleteProject(String proid) {
        try {
            projectService.deleteProject(proid);
            return ResponseEntity.ok("프로젝트 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("프로젝트 삭제 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/projects/select")
    public ResponseEntity<List<String>> getProjectsByEmail(String email) {
        List<byte[]> images = projectService.getProjectImagesByEmail(email);
        System.out.println(email + "의 프로젝트를 조회합니다.");
        if (images.isEmpty()) {
            System.out.println("프로젝트가 없습니다.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        List<String> base64Images = new ArrayList<>();
        for (byte[] image : images) {
            base64Images.add(java.util.Base64.getEncoder().encodeToString(image));
        }
        return ResponseEntity.status(HttpStatus.OK).body(base64Images);

    }

    @PostMapping("/project/update")
    public ResponseEntity<String> updateProject(String proid, String des) {
        try {
            // 업데이트 작업 수행
            projectService.updateProject(proid, des);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.ok().headers(headers).body("업데이트에 성공했습니다.");
        } catch (Exception e) {
            // 업데이트 작업 실패
            return ResponseEntity.badRequest().body("업데이트에 실패했습니다. 실패 이유: " + e.getMessage());
        }
    }


    @PostMapping("/project/save")
    public ResponseEntity<String> saveProject(@RequestParam("email") String email,
                                              @ModelAttribute ProjectDTO projectDTO,
                                              @RequestParam("projectImage") MultipartFile image) {
        try {
            projectService.saveProject(email, projectDTO, image);
            return ResponseEntity.ok("프로젝트 저장 완료");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로젝트 저장 중 오류가 발생했습니다.");
        }
    }

}
