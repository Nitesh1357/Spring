package com.zap.payment.otp.service.service;

import com.zap.payment.otp.service.exceptions.ErrorCode;
import com.zap.payment.otp.service.exceptions.ZAPException;
import com.zap.payment.otp.service.utils.RandomNumberUtil;
import com.zap.wallet.common.request.otp.SendOtpRequest;
import com.zap.wallet.common.request.otp.ValidateOTPRequest;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CustomLog
@Service
public class OTPServiceImpl implements OTPService {

    @Value("${otp.message}")
    private String OTP_MESSAGE;
    @Value("${otp.expiry.minutes}")
    private Long otpExpiryTimeInMinutes;
    @Value("${otp.retry.attempts}")
    private Integer maxOtpRetries;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SmsService smsService;

    public void sendOTPToUser(SendOtpRequest request){
        if(request.getPhoneNo() == null || request.getPhoneNo().equals("")) {
            throw new ZAPException(ErrorCode.MOBILE_NO_REQUIRED);
        }
        String otpRetryKey = "otpRetryCounter:%s".formatted(request.getFullPhoneNo());
        String mobileOtpKey = "otpForMobile:%s".formatted(request.getFullPhoneNo());
        String otp = redisTemplate.opsForHash().get(mobileOtpKey,request.getPhoneNo()) != null ?
                redisTemplate.opsForHash().get(mobileOtpKey,request.getPhoneNo()).toString() : RandomNumberUtil.random(6);
        redisTemplate.expire(mobileOtpKey, otpExpiryTimeInMinutes, TimeUnit.MINUTES);
        log.infof("SMS SEND: START Sending Client %s SMS OTP code %s to phone=%s%s (%s).", request.getClientIp(), otp,
                request.getCountryCode(), request.getPhoneNo(), request.getFullPhoneNo());
        redisTemplate.opsForValue().increment(otpRetryKey);
        redisTemplate.opsForHash().put(mobileOtpKey,request.getFullPhoneNo(),otp);
        String sms = String.format(OTP_MESSAGE, otp);
        try {
            smsService.sendOtpSms(request.getFullPhoneNo(), sms);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    public void validateUserOTP(ValidateOTPRequest validateOTPRequest){
        String otpRetryKey = "otpRetryCounter:%s".formatted(validateOTPRequest.getMobileNo());
        String mobileOtpKey = "otpForMobile:%s".formatted(validateOTPRequest.getMobileNo());
        // getting OTP From the Cache Memory
        String actualOtp = redisTemplate.opsForHash().get(mobileOtpKey,validateOTPRequest.getMobileNo()) != null ?
                redisTemplate.opsForHash().get(mobileOtpKey,validateOTPRequest.getMobileNo()).toString() : null;
        if(actualOtp == null){
            // OTP is not available so we will return an exception
            throw new ZAPException(ErrorCode.OTP_MISMATCH);
        }
        Long attemptsByPhone = redisTemplate.opsForValue().increment(otpRetryKey) - 1;
        // Now let's check to see if we hit rate limits and throw a general not allowed error as a mask
        if (attemptsByPhone > maxOtpRetries) {
            redisTemplate.delete(mobileOtpKey);
            redisTemplate.opsForValue().set(otpRetryKey, "0", otpExpiryTimeInMinutes, TimeUnit.MINUTES);
            throw new ZAPException.NotAllowed(ErrorCode.INTERNAL_ERROR);
        }
        if(!actualOtp.equals(validateOTPRequest.getOtpCode())){
            // OTP is not matching so we will return an exception
            throw new ZAPException(ErrorCode.OTP_MISMATCH);
        }
        redisTemplate.delete(mobileOtpKey);
        redisTemplate.opsForValue().set(otpRetryKey, "0", otpExpiryTimeInMinutes, TimeUnit.MINUTES);
    }
}
