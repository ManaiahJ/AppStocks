package com.manusoft.admindto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class UserAdminDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
}
