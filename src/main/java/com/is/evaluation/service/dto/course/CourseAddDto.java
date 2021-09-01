package com.is.evaluation.service.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseAddDto {
    private String description;
    private String rating;
    private String language;
    private String duration;
    private String image;
}
