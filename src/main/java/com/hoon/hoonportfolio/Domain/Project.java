package com.hoon.hoonportfolio.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proid;

    private String title;
    private String description;

    private String githubLink;

    private byte[] mainImage;

    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "email")
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<Photo> photos;

    @Builder
    public Project(String title, String imageUrl, String description, String githubLink, byte[] mainImage, User user, List<Photo> photos) {
        this.title = title;
        this.description = description;
        this.githubLink = githubLink;
        this.mainImage = mainImage;
        this.user = user;
        this.imageUrl = imageUrl;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setProject(this);
    }

}