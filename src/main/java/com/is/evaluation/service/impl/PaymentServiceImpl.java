package com.is.evaluation.service.impl;

import com.google.common.base.Strings;
import com.is.evaluation.exception.Message;
import com.is.evaluation.exception.MessageDescription;
import com.is.evaluation.model.entity.Course;
import com.is.evaluation.model.entity.Enrollment;
import com.is.evaluation.model.entity.Payment;
import com.is.evaluation.model.entity.User;
import com.is.evaluation.model.repository.CourseRepository;
import com.is.evaluation.model.repository.PaymentRepository;
import com.is.evaluation.service.CourseService;
import com.is.evaluation.service.PaymentService;
import com.is.evaluation.service.UserCourseService;
import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.course.*;
import com.is.evaluation.service.dto.payment.PaymentAddDto;
import com.is.evaluation.service.dto.payment.PaymentQueryDto;
import com.is.evaluation.service.dto.payment.PaymentUpdateDto;
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
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CourseService courseService;

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public List<PaymentQueryDto> getPaymentsByCourse(long pCourseId) {

        List<Payment> vPaymentList = paymentRepository.getPaymentByCourseId(pCourseId);
        List<PaymentQueryDto> vPaymentQueryDtoList = new ArrayList<>();
        for (Payment vPayment : vPaymentList){
            PaymentQueryDto vPaymentQueryDto = new PaymentQueryDto();
            BeanUtils.copyProperties(vPayment,vPaymentQueryDto);
            vPaymentQueryDtoList.add(vPaymentQueryDto);
        }
        return vPaymentQueryDtoList;
    }

    @Override
    public Payment getPaymentsById(long pPaymentId) {
        return paymentRepository.getPaymentById(pPaymentId);
    }

    @Override
    public PaymentQueryDto createPayment(PaymentAddDto pPaymentAddDto) {
        Payment vPayment = new Payment();
        BeanUtils.copyProperties(pPaymentAddDto,vPayment);
        vPayment.setCourse(courseService.getCourseById(pPaymentAddDto.getCourseId()));
        paymentRepository.save(vPayment);
        PaymentQueryDto vPaymentQueryDto = new PaymentQueryDto();
        BeanUtils.copyProperties(vPayment,vPaymentQueryDto);
        return vPaymentQueryDto;
    }

    @Override
    public PaymentQueryDto updatePayment(PaymentUpdateDto pPaymentUpdateDto, long pPaymentId) {
        Payment vPayment = paymentRepository.getPaymentById(pPaymentId);
        BeanUtils.copyProperties(pPaymentUpdateDto,vPayment);
        paymentRepository.save(vPayment);

        PaymentQueryDto vPaymentQueryDto= new PaymentQueryDto();
        BeanUtils.copyProperties(vPayment,vPaymentQueryDto);
        return vPaymentQueryDto;
    }
}
