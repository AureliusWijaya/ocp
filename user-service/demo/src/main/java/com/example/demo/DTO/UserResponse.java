package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Builder
@Data
public class UserResponse {
    private String noRek;
    private String nama;
    private String alamat;
    private String phone_number;
    private Double saldo_awal;
    private String status_nasabah;
}
