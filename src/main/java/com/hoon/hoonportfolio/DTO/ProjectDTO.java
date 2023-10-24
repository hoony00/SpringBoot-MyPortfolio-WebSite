package com.hoon.hoonportfolio.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

    private String title;
    private String description;
    private String github;
    private String email;

    @Builder
    public ProjectDTO(String title, String description, String github, String email) {
        this.title = title;
        this.description = description;
        this.github = github;
        this.email = email;
    }
}



