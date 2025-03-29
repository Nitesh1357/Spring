package com.zap.payment.otp.service.exceptions;

import lombok.Getter;

@Getter
public class CustomValidationException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public CustomValidationException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
