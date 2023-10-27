package com.hoon.hoonportfolio.CService;


import com.hoon.hoonportfolio.DTO.ProjectDTO;
import com.hoon.hoonportfolio.DTO.UserDTO;
import com.hoon.hoonportfolio.Domain.Project;
import com.hoon.hoonportfolio.Domain.UserEntity;
import com.hoon.hoonportfolio.Repository.ProjectRepository;
import com.hoon.hoonportfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *     프로젝트 관련 기능을 담당하는 Service
 *
 *   @version          1.00    2023.10.14
 *   @author
 */

@Slf4j // 로그를 위한 어노테이션
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // 프로젝트 정보 조회
    public List<ProjectDTO> getProjectsByEmail(String email) {
        List<Project> projects = projectRepository.findByUserEmail(email);

        if (projects.isEmpty()) {
            return Collections.emptyList();
        }

        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProjectDTO convertToDTO(Project project) {
        return ProjectDTO.builder()
                .title(project.getTitle())
                .description(project.getDescription())
                .github(project.getGithubLink())
                .email(project.getUser().getEmail())
                .proid(String.valueOf(project.getProid()))
                .build();
    }

    //프로젝트 내용 업데이트
    public void updateProject(String proid, String des){
        Optional<Project> project = projectRepository.findById(Long.parseLong(proid));
           project.ifPresent(selectProject -> {
                selectProject.setDescription(des);
                projectRepository.save(selectProject);
            });

    }

    //프로젝트 proid로 삭제
    public void deleteProject(String proid){
        Optional<Project> project = projectRepository.findById(Long.parseLong(proid));
        project.ifPresent(selectProject -> {
            projectRepository.delete(selectProject);
        });
    }





    // 프로젝트 이미지 조회
    public List<byte[]> getProjectImagesByEmail(String email) {
        List<Project> projects = projectRepository.findByUserEmail(email);
        if (projects.isEmpty()) {
            System.out.println("프로젝트가 없습니다.");
            return Collections.emptyList(); // 이미지가 없을 경우 빈 리스트 반환
        }
        System.out.println(email + "님의 조회된 프로젝트 개수 : " + projects.size());
        List<byte[]> images = new ArrayList<>();
        for (Project project : projects) {
            images.add(project.getMainImage());
        }
        return images;
    }



    /// 프로젝트 저장
    public void saveProject(String email, ProjectDTO projectDTO, MultipartFile image) throws IOException {
        UserEntity user = userRepository.findById(email).orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        Project project = Project.builder()
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .user(user)
                .githubLink(projectDTO.getGithub())
                .mainImage(image.getBytes())
                .build();

      projectRepository.save(project);
    }

}
