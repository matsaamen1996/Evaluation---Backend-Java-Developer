package com.is.evaluation.service.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddDto {
    private String username;
    private String name;
    private String email;
    private String password;
    private String country;
}
