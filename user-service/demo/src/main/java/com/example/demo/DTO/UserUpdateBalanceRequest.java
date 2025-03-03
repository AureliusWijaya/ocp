package com.example.demo.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateBalanceRequest {
    @NotNull
    private String noRek;
    @NotNull
    private Double saldo;
}
