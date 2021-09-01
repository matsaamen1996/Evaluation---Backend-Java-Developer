package com.is.evaluation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    @Getter
    private String code;

    public NotFoundException(String pCode, String pMessage) {
        super(pMessage);
        this.code = pCode;
    }
}
