package com.zap.payment.otp.service.controller;

import com.zap.payment.otp.service.service.OTPService;
import com.zap.wallet.common.request.otp.SendOtpRequest;
import com.zap.wallet.common.request.otp.ValidateOTPRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpRestController {

    @Autowired
    private OTPService otpService;

    @RequestMapping(value = "generate", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> sendOTPToUser(@RequestBody @Validated SendOtpRequest sendOtpRequest) {
        otpService.sendOTPToUser(sendOtpRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> validateOTP(@RequestBody @Validated ValidateOTPRequest validateOTPRequest) {

        otpService.validateUserOTP(validateOTPRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
