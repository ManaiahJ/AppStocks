package com.manusoft.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesDto {
    private String rolesName;
}
