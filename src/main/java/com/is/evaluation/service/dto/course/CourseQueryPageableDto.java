package com.is.evaluation.service.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseQueryPageableDto {
    private List<CourseQueryDto> courseQueryDtoList;
    private long totalRows;
}
