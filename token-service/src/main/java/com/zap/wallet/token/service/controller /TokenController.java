package com.zap.wallet.token.service.controller;

import com.zap.wallet.common.request.token.TokenDto;
import com.zap.wallet.token.service.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/generate")
    public ResponseEntity<?> generateToken(@RequestBody String userName) {
        TokenDto tokenDto = tokenService.generateToken(userName);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticateToken(@RequestBody TokenDto tokenDto) throws Exception {
        tokenService.validateToken(tokenDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
