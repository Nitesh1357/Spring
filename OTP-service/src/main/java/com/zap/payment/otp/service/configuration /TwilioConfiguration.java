package com.zap.payment.otp.service.configuration;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TwilioConfiguration {

    @Autowired
    private Environment environment;

    @Value("#{environment.twilio_account_sid}")
    private String accountSID;
    @Value("#{environment.twilio_auth_token}")
    private String authToken;

    @Bean
    public void initializeTwilio() {
        Twilio.init(accountSID, authToken);
    }

}
