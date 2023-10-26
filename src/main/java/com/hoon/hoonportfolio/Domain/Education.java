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
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;

    private String name;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserEntity user;

    @Builder
    public Education(String name, UserEntity user) {
        this.name = name;
        this.user = user;
    }
}