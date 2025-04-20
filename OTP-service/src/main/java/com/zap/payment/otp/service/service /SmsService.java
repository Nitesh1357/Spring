package com.zap.payment.otp.service.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.zap.payment.otp.service.exceptions.ErrorCode;
import com.zap.payment.otp.service.exceptions.ZAPException;
import com.zap.payment.otp.service.utils.DateTimeUtil;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CustomLog
@Service
public class SmsService {

    @Autowired
    private TwilioService twilioService;

    @Async
    public void sendOtpSms(String fullPhoneNumber, String sms) {
        twilioService.sendSms(fullPhoneNumber, sms);
    }

}
