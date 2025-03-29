package com.zap.payment.otp.service.exceptions;

import com.zap.payment.otp.service.dto.ApiError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getHttpStatus());
    }

    private ResponseEntity buildResponseEntityForValidation(ApiError apiError) {
        return new ResponseEntity(apiError, null, apiError.getHttpStatus());
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> handleValidationException(CustomValidationException ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setResponseCode(ex.getErrorCode());
        apiError.setResponseMessage(ex.getErrorMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ZAPException.class)
    public ResponseEntity<?> handleCustomException(ZAPException ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setResponseCode(ex.getErrorCode().getKey());
        apiError.setResponseMessage(ex.getErrorCode().getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        ApiError errorDetails = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getHttpStatus(), request);
    }

}
