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
@Table(name = "user")
public class User {

    @Id
    private String email;

    private String name;
    private String password;


    private byte[] profileImage;

    private String explanation;

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    private List<Career> careers;

    @OneToMany(mappedBy = "user")
    private List<Skill> skills;

    @OneToMany(mappedBy = "user")
    private List<Certification> certifications;



    @Builder
    public User(String name, String email, String password, String explanation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.explanation = explanation;
    }
}