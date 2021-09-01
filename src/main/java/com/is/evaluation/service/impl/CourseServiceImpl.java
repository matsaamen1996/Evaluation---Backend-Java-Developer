package com.is.evaluation.service.impl;

import com.google.common.base.Strings;
import com.is.evaluation.exception.Message;
import com.is.evaluation.exception.MessageDescription;
import com.is.evaluation.model.entity.Course;
import com.is.evaluation.model.entity.User;
import com.is.evaluation.model.entity.Enrollment;
import com.is.evaluation.model.repository.CourseRepository;
import com.is.evaluation.service.CourseService;
import com.is.evaluation.service.UserCourseService;
import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.course.*;
import com.is.evaluation.util.Constants;
import com.is.evaluation.util.Security;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCourseService userCourseService;



    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    

    @Override
    public CourseQueryPageableDto getCourse(String pState, int pPage, int pRowsNumber) {
        CourseQueryPageableDto vCourseQueryPageableDto = new CourseQueryPageableDto();
        if (Strings.isNullOrEmpty(pState)) {
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty,null);
        }
        long vTotalRows = courseRepository.getCountCoursesByState(pState);

        User vUser=null;
        if(!Security.getUserOfAuthenticatedUser().equals(Constants.USER_ANONYMOUS)) {
            vUser = userService.getUserByUserName(Security.getUserOfAuthenticatedUser());
        }
        List<Enrollment> vEnrollmentList = new ArrayList<>();
        if (vUser != null) {
             vEnrollmentList = userCourseService.getUserCourseByUserId(vUser.getId());
        }
        if (vTotalRows > 0) {
            List<Course> vCourseList = courseRepository.getCoursesPageableByState(pState, PageRequest.of(pPage, pRowsNumber));
            List<CourseQueryDto> vUserQueryDtoList = new ArrayList<>();
            for (Course vCourse : vCourseList) {
                CourseQueryDto vCourseQueryDto = new CourseQueryDto();
                BeanUtils.copyProperties(vCourse, vCourseQueryDto);
                if (vUser != null) {
                    vCourseQueryDto.setEnroll(false);
                }
                for(Enrollment vEnrollment : vEnrollmentList){
                    if(vEnrollment.getCourse().getId().equals(vCourse.getId())){
                        vCourseQueryDto.setEnroll(true);
                    }
                }
                vUserQueryDtoList.add(vCourseQueryDto);
            }
            vCourseQueryPageableDto.setTotalRows(vTotalRows);
            vCourseQueryPageableDto.setCourseQueryDtoList(vUserQueryDtoList);
        } else {
            vCourseQueryPageableDto.setTotalRows(0);
        }
        return vCourseQueryPageableDto;
    }

    @Override
    public CourseQueryDto addCourse(CourseAddDto pCourseAddDto) {
        Course vCourse = new Course();
        BeanUtils.copyProperties(pCourseAddDto, vCourse);
        courseRepository.save(vCourse);
        CourseQueryDto vCourseQueryDto = new CourseQueryDto();
        BeanUtils.copyProperties(vCourse, vCourseQueryDto);
        return vCourseQueryDto;
    }

    @Override
    public CourseQueryDto updateCourse(long pCourseId, CourseUpdateDto pCourseUpdateDto) {
        Course vCourse = courseRepository.getCourseById(pCourseId).orElse(null);
        if (vCourse == null) {
            Object[] obj = {"Course", "id", String.valueOf(vCourse)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        BeanUtils.copyProperties(pCourseUpdateDto, vCourse);
        courseRepository.save(vCourse);
        CourseQueryDto vCourseQueryDto = new CourseQueryDto();
        BeanUtils.copyProperties(vCourse, vCourseQueryDto);
        return vCourseQueryDto;
    }

    @Override
    public void deleteCourse(long pCourseId) {
        Course vCourse = courseRepository.getCourseById(pCourseId).orElse(null);
        if (vCourse == null) {
            Object[] obj = {"Course", "id", String.valueOf(pCourseId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        vCourse.setDeletedDate(new Date());
        vCourse.setState(Constants.STATE_DELETED);
        courseRepository.save(vCourse);
    }



    @Override
    public Course getCourseById(long pCourseId) {
        return courseRepository.getCourseById(pCourseId).orElse(null);
    }


}
