package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ApiResponse <T>{
    public String status;
    public String message;
    public T data;
    public Instant timestamp;

    public ApiResponse(String status, String message, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = Instant.now();
    }
}
