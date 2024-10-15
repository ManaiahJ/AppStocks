package com.manusoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private UUID id;
    private String userName;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLogin;
    private LocalDateTime lastPasswordChange;
    private boolean passwordChange;
    private String email;
    private Long mobileNumber;
    private boolean enableEmailUpdates;
    private String role;

   /* public void addRole(RolesDto role){
        if(Objects.isNull(role)){
           roles = new LinkedHashSet<>();
        }

        roles.add(role);
    }*/
}
