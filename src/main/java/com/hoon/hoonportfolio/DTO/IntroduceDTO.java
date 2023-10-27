package com.hoon.hoonportfolio.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntroduceDTO {
    private String eid;
    private String cid;
    private String sid;
    private String educationName;
    private String certificationName;
    private String skillName;

    @Builder
    public IntroduceDTO(String eid, String cid, String sid, String eName, String cName, String sName) {
        this.eid = eid;
        this.cid = cid;
        this.sid = sid;
        this.educationName = eName;
        this.certificationName = cName;
        this.skillName = sName;
    }
}


