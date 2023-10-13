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
@Table(name = "Portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String title;
    private String description;

    @OneToMany(mappedBy = "portfolio")
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolio")
    private List<Career> careers;

    @OneToMany(mappedBy = "portfolio")
    private List<Skill> skills;

    @OneToMany(mappedBy = "portfolio")
    private List<Certification> certifications;

    @OneToOne
    @JoinColumn(name = "uid")
    private UserEntity user;
}