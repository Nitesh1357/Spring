package com.zap.payment.otp.service.service;

import com.zap.wallet.common.request.otp.SendOtpRequest;
import com.zap.wallet.common.request.otp.ValidateOTPRequest;

public interface OTPService {

    void sendOTPToUser(SendOtpRequest request);

    void validateUserOTP(ValidateOTPRequest validateOTPRequest);

}
