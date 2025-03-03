package com.example.demo.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nasabah")
public class UserEntity {

    @Id
    @Column(name = "no_rekening")
    private String noRek;

    @Column(name = "password")
    private String password;

    @Column(name = "nik")
    private String NIK;

    @Column(name = "nama")
    private String name;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "no_hp")
    private String phoneNumber;

    @Column(name = "saldo_awal")
    private Double saldoAwal;

    @Column(name = "status_nasabah")
    private String statusNasabah;

}