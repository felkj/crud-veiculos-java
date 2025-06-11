package com.felkj.veiculos_api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminRawPassword = "admin123";
        String userRawPassword = "user123";

        String adminHashedPassword = encoder.encode(adminRawPassword);
        String userHashedPassword = encoder.encode(userRawPassword);
        //Usei para converter a senha e usuario para hash e inserir no banco
        System.out.println("Hash para 'SenhaDeADMIN': " + adminHashedPassword);
        System.out.println("Hash para 'SenhaDeUSER': " + userHashedPassword);
    }
}
