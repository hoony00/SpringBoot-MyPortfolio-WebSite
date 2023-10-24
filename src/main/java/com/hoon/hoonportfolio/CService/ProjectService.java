package com.hoon.hoonportfolio.CService;


import com.hoon.hoonportfolio.DTO.ProjectDTO;
import com.hoon.hoonportfolio.Domain.Project;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.ProjectRepository;
import com.hoon.hoonportfolio.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *     프로젝트 관련 기능을 담당하는 Service
 *
 *   @version          1.00    2023.10.14
 *   @author
 */

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // 프로젝트 조화
    public byte[] getProjectsPhotoByEmail(String email) {
        // 사용자의 이메일로 프로젝트 목록을 조회
        List<Project> projects = projectRepository.findByUserEmail(email);
        // 프로젝트가 없는 경우 null 반환
        if (projects.isEmpty()) {
            return null;
        }
        // 프로젝트가 있는 경우 프로젝트의 이미지를 반환
        return projects.get(0).getMainImage();
    }

/*    public byte[] getProfileImage(String email) {
        System.out.println("getProfileImage 이메일: " + email);
        Optional<User> userOptional = userRepository.findById(email);
        // 사용자를 찾지 못한 경우 null 또는 기본 이미지 반환
        return userOptional.map(User::getProfileImage).orElse(null);
    }*/

    public List<byte[]> getProjectsByEmail(String email) {
        List<Project> projects = projectRepository.findByUserEmail(email);
        if (projects.isEmpty()) {
            System.out.println("프로젝트가 없습니다.");
            return Collections.emptyList(); // 이미지가 없을 경우 빈 리스트 반환
        }

        List<byte[]> images = new ArrayList<>();
        for (Project project : projects) {
            images.add(project.getMainImage());
        }
        return images;
    }



    //프로젝트 저장
    public void saveProject(String email, ProjectDTO projectDTO, MultipartFile image) throws IOException {
        UserEntity user = userRepository.findById(email).orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        Project project = Project.builder()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .user(user)
                .githubLink(projectDTO.getGithub())
                .mainImage(image.getBytes())
                .build();



        Project project1 = projectRepository.save(project);


    }






}
