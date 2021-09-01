package com.is.evaluation.service.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class UserQueryDto {
    private long id;
    private String username;
    private String name;
    private String email;
    private String country;
    private Date birthdate;
    private String gender;
    private String address;
    private String telephone;
    private String language;
    private String education;
    private Boolean completed;
}
