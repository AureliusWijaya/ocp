package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotNull
    private String Nik;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String alamat;
    @NotNull
    private String phone_number;
}
