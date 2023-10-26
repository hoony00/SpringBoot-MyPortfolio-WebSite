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
@Table(name = "career")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String name;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserEntity user;

    @Builder
    public Career(String name, UserEntity user) {
        this.name = name;
        this.user = user;
    }
}