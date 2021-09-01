package com.is.evaluation.service.impl;

import com.is.evaluation.exception.Message;
import com.is.evaluation.exception.MessageDescription;
import com.is.evaluation.model.entity.Course;
import com.is.evaluation.model.entity.Enrollment;
import com.is.evaluation.model.entity.Payment;
import com.is.evaluation.model.entity.User;
import com.is.evaluation.model.repository.CourseRepository;
import com.is.evaluation.model.repository.UserCourseRepository;
import com.is.evaluation.service.CourseService;
import com.is.evaluation.service.PaymentService;
import com.is.evaluation.service.UserCourseService;
import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.course.CourseRegisterQueryDto;
import com.is.evaluation.service.dto.enrollment.EnrollmentAddDto;
import com.is.evaluation.util.Constants;
import com.is.evaluation.util.Security;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    private final UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public List<Enrollment> getUserCourseByUserId(long pUserId) {
        return userCourseRepository.getUserCourseByUserId(pUserId);
    }

    @Override
    public void courseSubscribe(EnrollmentAddDto pEnrollmentAddDto) {

        Payment vPayment = paymentService.getPaymentsById(pEnrollmentAddDto.getPaymentId());
        Course vCourse = courseService.getCourseById(pEnrollmentAddDto.getCourseId());
        User vUser= userService.getUserByUsername(Security.getUserOfAuthenticatedUser());
        Enrollment vEnrollment = userCourseRepository.getUserCourseByUserIdAndCourseId(vCourse.getId(),vUser.getId()).orElse(null);

        if (vPayment == null) {
            Object[] obj = {"Payment", "id", String.valueOf(pEnrollmentAddDto.getPaymentId())};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        if (vCourse == null) {
            Object[] obj = {"Course", "id", String.valueOf(pEnrollmentAddDto.getCourseId())};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        if (vUser == null) {
            Object[] obj = {"User", "username", String.valueOf(Security.getUserOfAuthenticatedUser())};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        if(vEnrollment != null){
            Object[] obj = {Security.getUserOfAuthenticatedUser(),vCourse.getName()};
            throw Message.GetBadRequest(MessageDescription.RepeatedCourse, obj);
        }

        vEnrollment = new Enrollment();
        vEnrollment.setCourse(vCourse);
        vEnrollment.setUser(vUser);
        vEnrollment.setPayment(vPayment);
        userCourseRepository.save(vEnrollment);
    }

    @Override
    public List<CourseRegisterQueryDto> getSubscribe() {
        List<CourseRegisterQueryDto> vCourseRegisterQueryDtoList =new ArrayList<>();
        User vUser = userService.getUserByUserName(Security.getUserOfAuthenticatedUser());
        List<Enrollment> vEnrollmentList =  userCourseRepository.getUserCourseByUserId(vUser.getId());
        for(Enrollment vEnrollment : vEnrollmentList){
            CourseRegisterQueryDto vCourseRegisterQueryDto = new CourseRegisterQueryDto();
            Course vCourse = courseService.getCourseById(vEnrollment.getCourse().getId());
            BeanUtils.copyProperties(vCourse,vCourseRegisterQueryDto);
            vCourseRegisterQueryDto.setId(vEnrollment.getId()
            );
            vCourseRegisterQueryDtoList.add(vCourseRegisterQueryDto);
        }
        return vCourseRegisterQueryDtoList;
    }

    @Override
    public void deleteEnrollment(long pEnrollmentId) {
        Enrollment vEnrollment = userCourseRepository.getUserCourseById(pEnrollmentId);
        vEnrollment.setState(Constants.STATE_DELETED);
        userCourseRepository.save(vEnrollment);
    }

}
