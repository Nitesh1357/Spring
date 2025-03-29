package com.zap.payment.otp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;
    private String responseCode;
    private String responseMessage;
    private List<String> errors;

    private ApiError() {
        timeStamp = null;
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.responseMessage = ex.getMessage();
    }

    public ApiError(HttpStatus httpStatus, String responseMessage) {
        this();
        this.httpStatus = httpStatus;
        this.responseMessage = responseMessage;
    }

    public ApiError(HttpStatus httpStatus, String responseMessage, List<String> errors) {
        super();
        this.httpStatus = httpStatus;
        this.responseMessage = responseMessage;
        this.errors = errors;
    }

}
