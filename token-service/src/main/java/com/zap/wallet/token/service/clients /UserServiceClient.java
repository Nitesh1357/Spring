package com.zap.wallet.token.service.clients;

import com.zap.wallet.common.request.user.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping(value = "/validate")
    ResponseEntity<?> validateUser(@RequestBody LoginRequest dto);

}
