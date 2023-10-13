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

    private String name;
    private String email;
    private String password;

    @Column(name = "explanation", length = 10)
    private String explanation;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;



    @Builder
    public UserEntity( String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}