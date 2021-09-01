package com.is.evaluation.service.dto.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleQueryDto {
    private long id;
    private String role;
    private String description;
    private boolean assigned;
}
