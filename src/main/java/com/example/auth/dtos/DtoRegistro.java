package com.example.auth.dtos;

import lombok.Data;

@Data
public class DtoRegistro {
    private String username;
    private String last_name;
    private String email;
    private String password;
}
