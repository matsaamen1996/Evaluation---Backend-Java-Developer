package com.is.evaluation.controller;

import com.is.evaluation.service.UserCourseService;
import com.is.evaluation.service.dto.course.*;
import com.is.evaluation.service.dto.enrollment.EnrollmentAddDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "endpoints for enrollment management")
@RestController
@RequestMapping("api/enrolment")
public class EnrollmentController {

    private final UserCourseService enrollmentService;

    public EnrollmentController(UserCourseService enrollmentService){

        this.enrollmentService = enrollmentService;
    }


    @ApiOperation(value = "Enrollment course", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public void courseEnrollment(@ApiParam(value = "Enrolment data") @RequestBody EnrollmentAddDto pEnrollmentAddDto){
        enrollmentService.courseSubscribe(pEnrollmentAddDto);
    }

    @ApiOperation(value = "Gets a list of all courses enrollment", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseRegisterQueryDto> getEnrollment(){
        return enrollmentService.getSubscribe();
    }

    @ApiOperation(value = "delete course enrollment", authorizations = @Authorization(value = "Bearer"))
    @DeleteMapping(path = "{pEnrollmentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEnrollment(@ApiParam(value = "Enrollment identification") @PathVariable Long pEnrollmentId){
        enrollmentService.deleteEnrollment(pEnrollmentId);
    }


}
