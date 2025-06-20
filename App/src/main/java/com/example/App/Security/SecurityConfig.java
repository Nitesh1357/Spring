
package com.example.App.Security;

import com.example.App.Utility.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("🔐 Configuring SecurityFilterChain...");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(
                                    "/auth/**", "/register",
                                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                    "/login", "/oauth2/**" // OAuth2 login entrypoints
                            ).permitAll();

                    auth.requestMatchers("/api/products/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint((request, response, authException) -> {
                            logger.warn("❌ Unauthorized access attempt: {}", authException.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                        })
                )
                // ✅ Add form login
                .formLogin(Customizer.withDefaults())

                // ✅ Add Google OAuth2 login
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true) // update URL as needed
                )

                // ✅ Add JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}















// JWT fully working
//package com.example.App.Security;
//
//import com.example.App.Utility.JwtAuthFilter;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.*;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        logger.info("🔐 Configuring SecurityFilterChain...");
//
//        http
//                .csrf(csrf -> {
//                    csrf.disable();
//                    logger.debug("CSRF protection disabled for stateless REST API.");
//                })
//                .authorizeHttpRequests(auth -> {
//                    auth
//                            .requestMatchers(
//                                    "/auth/**",
//                                    "/register",
//                                    "/v3/api-docs/**",
//                                    "/swagger-ui/**",
//                                    "/swagger-ui.html"
//                            ).permitAll();
//                    logger.debug("✔ Public endpoints permitted: /auth/**, /register, Swagger");
//
//                    auth
//                            .requestMatchers("/api/products/**").permitAll();
//                    logger.debug("✔ /api/products/** endpoint permitted");
//
//                    auth.anyRequest().authenticated();
//                    logger.debug("🔐 All other endpoints require authentication.");
//                })
//                .sessionManagement(session -> {
//                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                    logger.debug("Session creation policy set to STATELESS.");
//                })
//                .exceptionHandling(ex -> {
//                    ex.authenticationEntryPoint((request, response, authException) -> {
//                        logger.warn("❌ Unauthorized access attempt: {}", authException.getMessage());
//                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//                    });
//                })
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // ❌ Disabled login form and basic auth (JWT-only)
//
//
////         .formLogin(Customizer.withDefaults());
////         .httpBasic(Customizer.withDefaults());
//
//        logger.info("✅ SecurityFilterChain configuration completed.");
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        logger.info("🔐 PasswordEncoder (BCrypt) initialized.");
//        return new BCryptPasswordEncoder();
//    }
////
//    @Bean
//    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//        logger.info("🔐 AuthenticationManager bean initialized.");
//        return config.getAuthenticationManager();
//    }
//}


















// Oauth and user login working
//package com.example.App.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//
//import com.example.App.Utility.JwtAuthFilter;
//import com.example.App.Service.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.*;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       Oauth with form login dissable
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**", "/register").permitAll()
//                        .requestMatchers("/api/products/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
//                        )
//                )
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(form -> form.permitAll());




    //swagger integration start



//jwt auth with form login dissable
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**", "/register").permitAll()
//                        .requestMatchers("/api/products/**").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, ex1) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex1.getMessage())
//                        )
//                )
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(AbstractHttpConfigurer::disable); // ✅ no login form
//
//
//        return http.build();
//    }


//old program
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}



//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/products/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form.permitAll());
//               .oauth2Login(Customizer.withDefaults());
//        return http.build();
//    }
//}

