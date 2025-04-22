package com.zap.payment.otp.service.controller;

import com.zap.wallet.common.request.otp.SendOtpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OTPControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String URL;

    @BeforeEach
    public void setUp() {
        URL = "http:localhost:" + port + "/api/v1/otp/";
    }

//    @Test
    public void generateOtpTest() throws Exception {
        SendOtpRequest request = new SendOtpRequest("+92", "03006976489",
                "+923006976489", null, null);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(URL + "generate",
                request, ResponseEntity.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
