package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSetorResponse {
    private String noRek;
    private String nama;
    private Double saldo_awal;
}
