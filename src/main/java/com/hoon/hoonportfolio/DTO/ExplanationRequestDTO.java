package com.hoon.hoonportfolio.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExplanationRequestDTO {

    private String email;
    private String explanation;

    @Builder
    public ExplanationRequestDTO(String email, String explanation) {
        this.email = email;
        this.explanation = explanation;
    }


}
