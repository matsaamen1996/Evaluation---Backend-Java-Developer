package com.is.evaluation.controller;

import com.is.evaluation.service.PaymentService;
import com.is.evaluation.service.dto.payment.PaymentAddDto;
import com.is.evaluation.service.dto.payment.PaymentQueryDto;
import com.is.evaluation.service.dto.payment.PaymentUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "endpoints for payment management")
@RestController
@RequestMapping("api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){

        this.paymentService = paymentService;
    }

    @ApiOperation(value = "Create a payment", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public PaymentQueryDto createPayment(@ApiParam(value = "Payment data") @RequestBody PaymentAddDto pPaymentAddDto){
        return paymentService.createPayment(pPaymentAddDto);
    }

    @ApiOperation(value = "Gets a list of payment by course", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentQueryDto> getPaymentsByCourse(@ApiParam(value = "Course identification") @RequestParam long pCourseId){
        return paymentService.getPaymentsByCourse(pCourseId);
    }

    @ApiOperation(value = "Update a payment", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "{pPaymentId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentQueryDto updatePayment(@ApiParam(value = "Payment identification") @RequestParam long pPaymentId,
                                         @ApiParam(value = "Payment data") @RequestBody PaymentUpdateDto pPaymentUpdateDto){
        return paymentService.updatePayment(pPaymentUpdateDto,pPaymentId);
    }


}
