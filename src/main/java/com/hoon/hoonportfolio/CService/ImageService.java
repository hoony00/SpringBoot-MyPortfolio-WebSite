package com.hoon.hoonportfolio.CService;


import com.hoon.hoonportfolio.Domain.Photo;
import com.hoon.hoonportfolio.Domain.Project;
import com.hoon.hoonportfolio.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *     유저 관련 기능을 담당하는 Service
 *
 *   @version          1.00    2023.10.14
 *   @author
 */

@Service
public class ImageService {
    @Autowired
    private ProjectRepository projectRepository;

    public void uploadMainPhoto(Long projectId, byte[] imageBytes) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isPresent()){
            Project p = project.get();
            p.setMainImage(imageBytes);
            projectRepository.save(p);
        }else{
            System.out.println("uploadMainPhoto");
        }


    }

    public void uploadPhoto(Long projectId, byte[] imageBytes) {
        Optional<Project> project = projectRepository.findById(projectId);

        if(project.isPresent()) {
            Photo photo = new Photo();
            photo.setPhotoData(imageBytes);
            Project p = project.get();
            p.getPhotos().add(photo);
            photo.setProject(p);
            projectRepository.save(p);
        }else {
            System.out.println("uploadPhoto");
        }


    }
}
