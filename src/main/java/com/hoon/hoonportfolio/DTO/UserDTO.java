package com.hoon.hoonportfolio.DTO;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String pwcheck;
    private String explanation;


    @Builder
    public UserDTO(String name, String email, String password, String pwcheck, String explanation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pwcheck = pwcheck;
        this.explanation = explanation;
    }
}
