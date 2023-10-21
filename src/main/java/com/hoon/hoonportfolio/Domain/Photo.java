package com.hoon.hoonportfolio.Domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "Project")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phid;

    @Lob
    private byte[] photoData; // 사진의 BLOB 데이터

    @ManyToOne
    @JoinColumn(name = "proid")
    private Project project;

}