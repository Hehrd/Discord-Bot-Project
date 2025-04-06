package com.alexander.controller.model;

import lombok.Data;

@Data
public class UserCredentialsDTO extends DTO {
    private String email;
    private String password;
}
