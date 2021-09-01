package com.is.evaluation.service.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseUpdateDto {
    private String name;
    private Date date;
    private String author;
}
