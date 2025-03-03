package com.example.demo.controller;


import com.example.demo.DTO.*;
import com.example.demo.model.ApiResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.CurrentUtil;
import com.example.demo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ResponseUtil.success(
                "Data retrieved successfully", users
                ));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(){
        String name = CurrentUtil.getCurrentUser();
        System.out.println("ini name log: "+ name);
        UserResponse user = userService.getProfile(name);
        return ResponseEntity.ok(ResponseUtil.success(
                "Successfully retrieved profile data", user));
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserUpdateRequest>> edit(
            @Valid @RequestBody UserUpdateRequest userRequest
    ){
        String name = CurrentUtil.getCurrentUser();

        userService.updateProfile(userRequest, name);

        return ResponseEntity.ok(ResponseUtil.success(
                "Data has been updated", userRequest));
    }
    @PutMapping("/update/balance")
    public ResponseEntity<ApiResponse<UserSetorResponse>> editBalance(
            @Valid @RequestBody UserUpdateBalanceRequest userRequest){

        return ResponseEntity.ok(ResponseUtil.success(
                "Data has been updated",
                userService.updateBalance(userRequest)));
    }
    @PutMapping("/update/balance/kurang")
    public ResponseEntity<ApiResponse<UserTarikResponse>> editBalanceTarik(
            @Valid @RequestBody UserUpdateBalanceRequest userRequest){

        return ResponseEntity.ok(ResponseUtil.success(
                "Data has been updated",
                userService.updateBalanceTarik(userRequest)));
    }

    @DeleteMapping("/tutup")
    public ResponseEntity<ApiResponse<UserResponse>> tutupAkun(){
        String name = CurrentUtil.getCurrentUser();
        return ResponseEntity.ok(ResponseUtil.success("User has been deleted", userService.tutupAkun(name)));
    }

}
