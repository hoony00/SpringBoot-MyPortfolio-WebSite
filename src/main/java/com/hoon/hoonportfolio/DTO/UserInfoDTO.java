package com.hoon.hoonportfolio.DTO;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.hoon.hoonportfolio.Domain.UserEntity}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {
    String name;
    byte[] profileImage;
    String explanation;
}