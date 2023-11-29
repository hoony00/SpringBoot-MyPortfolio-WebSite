package com.hoon.hoonportfolio.Domain;

import com.hoon.hoonportfolio.constant.Role;
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
public class UserEntity {

    @Id
    private String email;

    private String name;

    private String password;

    //@Lob
    private byte[] profileImage;

    private String explanation;

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    private List<Education> careers;

    @OneToMany(mappedBy = "user")
    private List<Skill> skills;

    @OneToMany(mappedBy = "user")
    private List<Certification> certifications;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public UserEntity(String name, Role role, String email, String password, String explanation) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.explanation = explanation;
    }
}