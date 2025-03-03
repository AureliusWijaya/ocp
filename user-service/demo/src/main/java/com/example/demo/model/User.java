package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Builder
public class User {
    private int id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;
    @Override
    public String toString(){
        return "Customer Name: " + name+
                " Address: " + address+
                " Phone number: " + phoneNumber+
                " Email: " + email;
    }
}
