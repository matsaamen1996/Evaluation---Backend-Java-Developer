package com.is.evaluation.service;


import com.is.evaluation.model.entity.Payment;
import com.is.evaluation.service.dto.payment.PaymentAddDto;
import com.is.evaluation.service.dto.payment.PaymentQueryDto;
import com.is.evaluation.service.dto.payment.PaymentUpdateDto;

import java.util.List;

public interface PaymentService {
    List<PaymentQueryDto> getPaymentsByCourse (long pCourseId);

    Payment getPaymentsById (long pPaymentId);

    PaymentQueryDto createPayment(PaymentAddDto pPaymentAddDto);

    PaymentQueryDto updatePayment(PaymentUpdateDto pPaymentUpdateDto, long pPaymentId);


}
