package com.example.demo.service;


import com.example.demo.DTO.*;
import com.example.demo.config.JWTUtil;
import com.example.demo.repository.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ResourceNotFoundException;
import com.example.demo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    public List<UserResponse> getAllUsers(){
        List<UserEntity> user = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(UserEntity i : user){
            userResponses.add(UserResponse.builder()
                            .nama(i.getName())
                            .phone_number(i.getPhoneNumber())
                            .saldo_awal(i.getSaldoAwal())
                            .status_nasabah(i.getStatusNasabah())
                            .alamat(i.getAlamat())
                            .noRek(i.getNoRek())
                    .build());
        }
        return userResponses;
    }



    public UserResponse getProfile(String name){
        UserEntity user = userRepository.findByName(name);

        return UserResponse.builder()
                .nama(user.getName())
                .phone_number(user.getPhoneNumber())
                .saldo_awal(user.getSaldoAwal())
                .status_nasabah(user.getStatusNasabah())
                .alamat(user.getAlamat())
                .noRek(user.getNoRek())
                .build();
    }

    public String loginAccount(UserLoginRequest userLoginRequest){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getName(), userLoginRequest.getPassword());
            authManager.authenticate(authInputToken);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
        return jwtUtil.generateToken(userLoginRequest.getName());
    }

    public void registerAccount(UserRegisterRequest userRequest){
        UserEntity user = userRepository.findByName(userRequest.getName());
        if(user != null){
            throw new ResourceNotFoundException("Username has already been registered");
        }
        SecureRandom random = new SecureRandom();
        Long rand = 10000000000L + random.nextLong();
        String encodedPass = passwordEncoder.encode(userRequest.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .NIK(userRequest.getNik())
                .name(userRequest.getName())
                .password(encodedPass)
                .alamat(userRequest.getAlamat())
                .saldoAwal(0.0)
                .statusNasabah("aktif")
                .noRek(String.valueOf(rand))
                .phoneNumber(userRequest.getPhone_number())
                .build();

        userRepository.save(userEntity);
    }

    public UserUpdateRequest updateProfile(UserUpdateRequest userRequest, String name){
        UserEntity userEntity = userRepository.findByName(name);
        userEntity.setPhoneNumber(userRequest.getPhone_number());
        userEntity.setAlamat(userRequest.getAlamat());
        userEntity.setName(userRequest.getName());

        userRepository.save(userEntity);

        return UserUpdateRequest.builder()
                .name(userEntity.getName())
                .alamat(userEntity.getAlamat())
                .phone_number(userEntity.getPhoneNumber())
                .build();
    }

    public UserResponse tutupAkun(String name){
        UserEntity userEntity = userRepository.findByName(name);
        userEntity.setStatusNasabah("non-aktif");
        userRepository.save(userEntity);
        return UserResponse.builder()
                .nama(userEntity.getName())
                .phone_number(userEntity.getPhoneNumber())
                .saldo_awal(userEntity.getSaldoAwal())
                .status_nasabah(userEntity.getStatusNasabah())
                .alamat(userEntity.getAlamat())
                .noRek(userEntity.getNoRek())
                .build();
    }

    public UserSetorResponse updateBalance(UserUpdateBalanceRequest userRequest){
        UserEntity userEntity = userRepository.findBynoRek(userRequest.getNoRek());
        userEntity.setSaldoAwal(userEntity.getSaldoAwal() + userRequest.getSaldo());
        userRepository.save(userEntity);

        return UserSetorResponse.builder()
                .noRek(userRequest.getNoRek())
                .saldo_awal(userEntity.getSaldoAwal())
                .nama(userEntity.getName())
                .build();
    }

    public UserTarikResponse updateBalanceTarik(UserUpdateBalanceRequest userRequest){
        UserEntity userEntity = userRepository.findBynoRek(userRequest.getNoRek());
        userEntity.setSaldoAwal(userEntity.getSaldoAwal() - userRequest.getSaldo());
        userRepository.save(userEntity);

        return UserTarikResponse.builder()
                .noRek(userRequest.getNoRek())
                .balance(userEntity.getSaldoAwal())
                .nama(userEntity.getName())
                .build();
    }

}
