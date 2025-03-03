package com.example.demo.controller;

import com.example.demo.DTO.UserLoginRequest;
import com.example.demo.DTO.UserRegisterRequest;
import com.example.demo.config.JWTUtil;
import com.example.demo.model.ApiResponse;
import com.example.demo.repository.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

     private final UserRepository userRepo;
     private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerHandler(@Valid @RequestBody UserRegisterRequest user){
        userService.registerAccount(user);
        String token = jwtUtil.generateToken(user.getName());
        return ResponseEntity.ok(ResponseUtil.success(
                "Register Success", token));

    }

     @PostMapping("/login")
     public ResponseEntity<ApiResponse<String>> loginHandler(@RequestBody UserLoginRequest body){
             return ResponseEntity.ok(ResponseUtil.success(
                     "Login Success", userService.loginAccount(body)
             ));
     }

}
