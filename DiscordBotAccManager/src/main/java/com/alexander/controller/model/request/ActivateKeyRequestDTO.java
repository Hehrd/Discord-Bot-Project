package com.alexander.controller.model.request;


import lombok.Data;

@Data
public class ActivateKeyRequestDTO {
    private String accessToken;
    private String apiKey;
}
