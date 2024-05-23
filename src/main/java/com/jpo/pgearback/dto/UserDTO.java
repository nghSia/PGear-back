package com.jpo.pgearback.dto;

import com.jpo.pgearback.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private UserRole role;
    private Integer points;
}
