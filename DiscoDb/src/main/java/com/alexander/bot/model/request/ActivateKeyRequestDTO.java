package com.alexander.bot.model.request;


import lombok.Data;

@Data
public class ActivateKeyRequestDTO {
    private String accessToken;
    private String apiKey;
}
