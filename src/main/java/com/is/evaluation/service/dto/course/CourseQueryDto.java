package com.is.evaluation.service.dto.course;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class CourseQueryDto {
    private long id;
    private String description;
    private String rating;
    private String language;
    private String duration;
    private String image;
    private Boolean enroll;

}
