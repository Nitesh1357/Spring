package com.zap.payment.otp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zap.payment.otp.service.exceptions.ZAPException;
import com.zap.payment.otp.service.service.SmsService;
import com.zap.wallet.common.request.otp.SendOtpRequest;
import com.zap.wallet.common.request.otp.ValidateOTPRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class OtpRestControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;
    @LocalServerPort
    private int port;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Mock
    private ValueOperations valueOperations;
    @Mock
    private HashOperations hashOperations;
    @MockBean
    private SmsService smsService;

    private String URL;

    @BeforeEach
    public void setUp() {
        URL = "http://localhost:" + port + "/api/v1/otp";
        doNothing().when(smsService).sendOtpSms(Mockito.anyString(), Mockito.anyString());
//        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void givenOTPRequest_whenOTPIsSentToUser_OTPIsSentSuccessfully() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(URL + "/generate")
                        .content(
                                asJsonString(new SendOtpRequest("+92", "03006976489", "+923006976489",
                                        "127.0.0.1", new String[]{""})))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenOTPRequest_whenCountryCodeIsMissing_ThenReturnsBadRequestError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(URL + "/generate")
                        .content(
                                asJsonString(new SendOtpRequest(null, "03006976489", "+923006976489",
                                        "127.0.0.1", new String[]{""})))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Invalid OTP")
    public void givenValidateOTPRequest_whenOtpIsNotValid_ThenReturnsMisMatchError() throws Exception {
        try {
            mvc.perform(MockMvcRequestBuilders
                    .post(URL + "/validate")
                    .content(
                            asJsonString(new ValidateOTPRequest("03006976489", "984719")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        } catch (NestedServletException ex) {
            Exception exception = Assertions.assertThrows(ZAPException.class, () -> {
                throw ex.getCause();
            });
            assertEquals(exception.getMessage(), "OTP does not match with the actual OTP");
        }
    }

    @Test
    public void givenValidateOTPRequest_whenOtpIsValid_ThenReturnsSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(URL + "/generate")
                        .content(
                                asJsonString(new SendOtpRequest("+92", "03006976489", "+923006976489",
                                        "127.0.0.1", new String[]{""})))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //validate OTP
        mvc.perform(MockMvcRequestBuilders
                        .post(URL + "/validate")
                        .content(
                                asJsonString(new ValidateOTPRequest("03006976489",
                                        redisTemplate.opsForHash().get("mobileNo","03006976489").toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
