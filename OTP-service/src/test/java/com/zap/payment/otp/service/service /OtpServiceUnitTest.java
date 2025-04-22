package com.zap.payment.otp.service.service;

import com.zap.payment.otp.service.exceptions.ZAPException;
import com.zap.wallet.common.request.otp.SendOtpRequest;
import com.zap.wallet.common.request.otp.ValidateOTPRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class OtpServiceUnitTest {

    @InjectMocks
    private OTPService otpService = new OTPServiceImpl();
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @MockBean
    private SmsService smsService;
    @MockBean
    private PropertySourcesPropertyResolver propertySourcesPropertyResolver;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(otpService, "OTP_MESSAGE",
                "Welcome, %s is your one time password (OTP). Please enter the OTP to proceed.\nThank you");
        MockitoAnnotations.openMocks(this);
        Whitebox.setInternalState(otpService, "redisTemplate", redisTemplate);
    }

    @Test
    @DisplayName("Generated OTP-SUCCESS")
    public void givenOTPRequest_whenOTPIsSentToUser_OTPIsSentSuccessfully() {
        SendOtpRequest request = new SendOtpRequest("+92", "03006976489",
                "+923006976489", null, null);
        Assertions.assertDoesNotThrow(() -> otpService.sendOTPToUser(request));
    }

    @Test
    @DisplayName("Generate OTP: Mobile No. Required")
    public void givenOTPRequest_whenOTPRequestNotContainValidMobileNo_OTPNotSent() {
        SendOtpRequest request = new SendOtpRequest("+92", null,
                "+923006976489", null, null);
        Exception exception = assertThrows(ZAPException.class, () -> {
            otpService.sendOTPToUser(request);
        });
        String expectedMessage = "Mobile No. is required";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Validate OTP-SUCCESS")
    public void givenOTPRequest_whenOTPIsValidated_OTPValidatedSuccessfully() {
        SendOtpRequest request = new SendOtpRequest("+92", "03006976489",
                "+923006976489", null, null);
        Assertions.assertDoesNotThrow(() -> otpService.sendOTPToUser(request));
        ValidateOTPRequest validateOTPRequest = new ValidateOTPRequest(request.getPhoneNo(),
                redisTemplate.opsForHash().get("mobileNo",request.getPhoneNo()).toString());
        Assertions.assertDoesNotThrow(() -> otpService.validateUserOTP(validateOTPRequest));
    }

}
