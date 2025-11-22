
package com.example.App.Controller;

import com.example.App.Model.JwtRequest;
import com.example.App.Model.JwtResponse;
import com.example.App.Repository.UserRepository;
import com.example.App.Utility.JwtUtil;
import com.example.App.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    // ✅ Login Endpoint
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        logger.info("🔐 Login attempt for email: {}", request.getEmail());

        try {
            Optional<User> userOpt = userRepo.findByEmail(request.getEmail());

            if (userOpt.isEmpty()) {
                logger.warn("❌ User with email {} not found", request.getEmail());
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            User user = userOpt.get();

            if (!encoder.matches(request.getPassword(), user.getPassword())) {
                logger.warn("❌ Password mismatch for email: {}", request.getEmail());
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            // If matched, authenticate and generate token
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtil.generateToken(user.getEmail());
            logger.info("✅ JWT generated for email: {}", user.getEmail());

            return ResponseEntity.ok(new JwtResponse(token));

        } catch (Exception e) {
            logger.error("❌ Exception during login: {}", e.getMessage());
            return ResponseEntity.status(500).body("Something went wrong during login");
        }
    }

    // ✅ Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        logger.info("📝 Registering user: {}", user.getEmail());

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("❗ Email already exists: {}", user.getEmail());
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        logger.info("✅ User registered: {}", user.getEmail());
        return ResponseEntity.ok("User registered successfully");
    }
}
















//package com.example.App.Controller;
//import com.example.App.Repository.UserRepository;
//import com.example.App.Security.SecurityConfig;
//import com.example.App.Utility.JwtUtil;
//import com.example.App.entity.User;
//import com.example.App.Model.JwtRequest;
//import com.example.App.Model.JwtResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    // ✅ Login endpoint
//    @PostMapping("/auth/login")
//    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
//        logger.info("🔐 Attempting login for user: {}", request.getPassword());
//        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        String token = jwtUtil.generateToken(request.getEmail());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    // ✅ Register endpoint
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody User user) {
//        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body("Email already exists");
//        }
//        user.setPassword(encoder.encode(user.getPassword()));
//        userRepo.save(user);
//        return ResponseEntity.ok("User registered successfully");
//    }
//}




