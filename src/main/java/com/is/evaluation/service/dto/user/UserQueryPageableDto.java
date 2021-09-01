package com.is.evaluation.service.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserQueryPageableDto {
    private List<UserQueryDto> userQueryDtoList;
    private long totalRows;
}
