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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String email;
    private String name;
    private String password;

    private byte[] myPhoto;

    @Column(name = "explanation")
    private String explanation;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;



    @Builder
    public UserEntity( String name, String email, String password, String explanation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.explanation = explanation;
    }
}