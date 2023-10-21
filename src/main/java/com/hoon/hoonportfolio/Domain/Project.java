package com.hoon.hoonportfolio.Domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proid;

    private String title;
    private String description;
    private String photo;
    private String githubLink;

    private byte[] mainPhoto;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "project")
    private List<Photo> photos;
}