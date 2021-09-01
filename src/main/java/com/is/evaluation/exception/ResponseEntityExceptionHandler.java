package com.is.evaluation.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {
    String message = "";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception pEx, WebRequest pRequest) {
        pEx.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse("0", pEx.getMessage(), pRequest.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleServerNotFoundException(NotFoundException pEx, WebRequest pRequest) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(pEx.getCode(), pEx.getMessage(), ExceptionUtils.getStackTrace(pEx));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleServerBadRequestException(BadRequestException pEx, WebRequest pRequest) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(pEx.getCode(), pEx.getMessage(), ExceptionUtils.getStackTrace(pEx));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException pEx, HttpHeaders pHeaders, HttpStatus pStatus, WebRequest pRequest) {
        pEx.getBindingResult().getAllErrors().forEach(e -> {
            message += e.getDefaultMessage() + "\n";
        });
        ExceptionResponse exceptionResponse = new ExceptionResponse(pEx.getParameter().toString(), message, pEx.getBindingResult().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            ex.printStackTrace();
            ExceptionResponse exceptionResponse;
            if (ex instanceof HttpMessageNotReadableException) {
                exceptionResponse = new ExceptionResponse("000", "Tipo de Dato inválido", ex.getMessage());
            } else {
                exceptionResponse = new ExceptionResponse("000", ex.getMessage(), ExceptionUtils.getStackTrace(ex));
            }
            return super.handleExceptionInternal(ex, exceptionResponse, headers, status, request);
        } else {
            return super.handleExceptionInternal(ex, body, headers, status, request);
        }
    }
}
