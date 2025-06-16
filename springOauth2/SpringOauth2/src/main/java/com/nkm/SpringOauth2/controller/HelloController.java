package com.nkm.SpringOauth2.controller;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

        @GetMapping("/")
        public String hello() {
            return "Hello World";
        }
    }
//    @GetMapping("/")
//    public String home() {
//        return "<a href='/login'>Login with Google</a>";
//    }
//
//    @GetMapping("/user")
//    public String user(Principal principal) {
//        OAuth2User oAuth2User = (OAuth2User) principal;
//        return "Hello, " + oAuth2User.getAttribute("name");
//    }
//}
