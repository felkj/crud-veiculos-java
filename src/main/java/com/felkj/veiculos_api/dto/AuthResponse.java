package com.felkj.veiculos_api.dto;
//Vai enviar o token do json web token de volta para o front-end

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private List<String> roles;
}
