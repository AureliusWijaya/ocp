package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTarikResponse {
    private String noRek;
    private String nama;
    private Double balance;
}
