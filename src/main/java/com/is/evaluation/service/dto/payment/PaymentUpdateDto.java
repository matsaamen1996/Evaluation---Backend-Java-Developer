package com.is.evaluation.service.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentUpdateDto {
    private String type;
    private String totalAmount;
    private String amount;
    private String description;
    private String title;
}
