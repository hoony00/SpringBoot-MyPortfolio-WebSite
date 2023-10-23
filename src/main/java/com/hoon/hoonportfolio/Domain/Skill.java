package com.hoon.hoonportfolio.Domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String skillName;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}