package com.manusoft.dto;

import lombok.Data;

@Data
public class TokenDto {

    private String token;

    public TokenDto() {
    }
    public TokenDto(String token) {
        this.token = token;
    }
}
