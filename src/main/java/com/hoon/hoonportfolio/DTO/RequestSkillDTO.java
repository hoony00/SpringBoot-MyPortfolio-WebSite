package com.hoon.hoonportfolio.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSkillDTO {

    private String sid;
    private String skillName;

    @Builder
    public RequestSkillDTO(String sid, String skillName) {
        this.sid = sid;
        this.skillName = skillName;
    }

}



