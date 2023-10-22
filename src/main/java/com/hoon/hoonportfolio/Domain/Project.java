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
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proid;

    private String title;
    private String description;
    private String photo;
    private String githubLink;

    @Lob
    private byte[] mainImage;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn
    private List<Photo> photos;

}