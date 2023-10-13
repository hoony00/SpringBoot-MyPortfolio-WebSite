package com.hoon.hoonportfolio.Domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @ManyToOne
    @JoinColumn(name = "pid")
    private Portfolio portfolio;
}