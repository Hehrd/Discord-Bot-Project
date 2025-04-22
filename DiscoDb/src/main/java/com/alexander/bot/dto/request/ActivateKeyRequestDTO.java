package com.alexander.bot.dto.request;


import lombok.Data;

@Data
public class ActivateKeyRequestDTO {
    private String accessToken;
    private String apiKey;
}
