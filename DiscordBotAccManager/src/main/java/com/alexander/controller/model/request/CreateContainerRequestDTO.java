package com.alexander.controller.model.request;

import lombok.Data;

@Data
public class CreateContainerRequestDTO {
    String jwt;
    String name;
}
