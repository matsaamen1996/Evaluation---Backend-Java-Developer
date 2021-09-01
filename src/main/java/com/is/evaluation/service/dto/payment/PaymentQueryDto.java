package com.is.evaluation.service.dto.payment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PaymentQueryDto {
    private long id;
    private String type;
    private String totalAmount;
    private String amount;
    private String description;
    private String title;


}
