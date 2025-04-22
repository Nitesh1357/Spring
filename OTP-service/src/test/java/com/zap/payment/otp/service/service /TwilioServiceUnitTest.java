package com.zap.payment.otp.service.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TwilioServiceUnitTest {

    @InjectMocks
    private TwilioService twilioService = new TwilioService();
    private String mobileNo;
    private String sms;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mobileNo = "=923006976489";
        this.sms = "Welcome,785678 is your one time password (OTP). Please enter the OTP to proceed.\nThank you";
    }

    @Test
    @Disabled
    @DisplayName("Send SMS")
    public void givenSmsRequest_whenSentOtpToUser_SMSIsSentSuccessfully() {
        Assertions.assertDoesNotThrow(() -> twilioService.sendSms(this.mobileNo, this.sms));
    }

}
