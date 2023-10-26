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
@Table(name = "certification")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cerid;

    private String cerName;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserEntity user;

    @Builder
    public Certification(String cerName, UserEntity user) {
        this.cerName = cerName;
        this.user = user;
    }
}