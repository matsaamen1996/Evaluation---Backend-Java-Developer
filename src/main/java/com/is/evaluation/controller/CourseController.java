package com.is.evaluation.controller;

import com.is.evaluation.service.CourseService;
import com.is.evaluation.service.dto.course.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "endpoints for course management")
@RestController
@RequestMapping("api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @ApiOperation(value = "Gets a list of all courses for state", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public CourseQueryPageableDto getCourses(@ApiParam(value = "Course Status") @RequestParam String state,
                                                 @ApiParam(value = "Number of page to consult") @RequestParam int page,
                                                 @ApiParam(value = "Number of records to consult") @RequestParam int rowsNumber){
        return courseService.getCourse(state, page, rowsNumber);
    }

    @ApiOperation(value = "Create a course", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseQueryDto addCourse(@ApiParam(value = "Course data") @Valid @RequestBody CourseAddDto courseAddDto){
        return courseService.addCourse(courseAddDto);
    }

    @ApiOperation(value = "Update a course", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseQueryDto updateCourse(@ApiParam(value = "Course identification") @PathVariable Long courseId,
                                   @ApiParam(value = "Data to update") @Valid @RequestBody CourseUpdateDto courseUpdateDto){
        return courseService.updateCourse(courseId, courseUpdateDto);
    }

    @ApiOperation(value = "Delete course", authorizations = @Authorization(value = "Bearer"))
    @DeleteMapping(path = "{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@ApiParam(value = "Course identification") @PathVariable Long courseId){
        courseService.deleteCourse(courseId);
    }
}
