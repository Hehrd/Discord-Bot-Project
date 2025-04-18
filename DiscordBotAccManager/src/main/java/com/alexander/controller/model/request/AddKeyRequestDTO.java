package com.alexander.controller.model.request;


import lombok.Data;

@Data
public class AddKeyRequestDTO {
    private String discordId;
    private String apiKey;
}
