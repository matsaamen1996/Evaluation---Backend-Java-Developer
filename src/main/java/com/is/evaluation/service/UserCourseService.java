package com.is.evaluation.service;

import com.is.evaluation.model.entity.Enrollment;
import com.is.evaluation.service.dto.course.CourseRegisterQueryDto;
import com.is.evaluation.service.dto.enrollment.EnrollmentAddDto;

import java.util.List;

public interface UserCourseService {

    List<Enrollment> getUserCourseByUserId(long pUserId);

    void courseSubscribe(EnrollmentAddDto pEnrollmentAddDto);

    List<CourseRegisterQueryDto> getSubscribe();

    void deleteEnrollment(long pEnrollmentId);
}
