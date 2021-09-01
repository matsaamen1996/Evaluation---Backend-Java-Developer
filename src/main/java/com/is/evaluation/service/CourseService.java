package com.is.evaluation.service;

import com.is.evaluation.model.entity.Course;
import com.is.evaluation.service.dto.course.*;

import java.util.List;

public interface CourseService {

    CourseQueryPageableDto getCourse(String pState, int pPage, int pRowsNumber);

    CourseQueryDto addCourse(CourseAddDto pCourseAddDto);

    CourseQueryDto updateCourse(long pUserId, CourseUpdateDto pCourseUpdateDto);

    void deleteCourse(long pCourseId);

    Course getCourseById(long pCourseId);


}
