package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.ProjectService;
import com.hoon.hoonportfolio.DTO.ProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    // 이메일을 받아서 프로젝트 리스트 리턴
/*    @GetMapping("/projects/select")
    public ResponseEntity<List<String>> getProjectsImagesUrlsByEmail(String email) {
        List<Project> projects = projectService.getProjectsByEmail(email);
        if (projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        List<String> imageUrls = new ArrayList<>();
        for (Project project : projects) {
            // 이미지의 URL을 가져와서 리스트에 추가
            imageUrls.add(project.getMainImage().);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(imageUrls, headers, HttpStatus.OK);
    }*/

    @GetMapping("/projects/select")
    public ResponseEntity<List<String>> getProjectsByEmail(String email) {
        List<byte[]> images = projectService.getProjectImagesByEmail(email);
        // 리턴 내용 찍어보기
        System.out.println(email + "의 프로젝트를 조회합니다.");

        if (images.isEmpty()) {
            System.out.println("프로젝트가 없습니다.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        List<String> base64Images = new ArrayList<>();
        for (byte[] image : images) {
            // 이미지의 URL을 가져와서 리스트에 추가
            base64Images.add(java.util.Base64.getEncoder().encodeToString(image));
        }
        return ResponseEntity.status(HttpStatus.OK).body(base64Images);

    }



    @PostMapping("/project/save")
    public ResponseEntity<String> saveProject(@RequestParam("email") String email,
                                              @ModelAttribute ProjectDTO projectDTO,
                                              @RequestParam("projectImage") MultipartFile image) {
        try {
            System.out.println("====+++===프로젝트 저장 요청====+++=====");
            System.out.println(projectDTO);
            System.out.println("이메일: " + email);
             projectService.saveProject(email, projectDTO, image);
            System.out.println("====+++===프로젝트 저장 완료====+++=====");

            return ResponseEntity.ok("프로젝트 저장 완료");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로젝트 저장 중 오류가 발생했습니다.");
        }
    }










}
