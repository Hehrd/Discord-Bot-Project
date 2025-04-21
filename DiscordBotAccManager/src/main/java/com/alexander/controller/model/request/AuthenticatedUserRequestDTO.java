package com.alexander.controller.model.request;

import lombok.Data;

@Data
public class AuthenticatedUserRequestDTO {
    private String discordId;
    private String apiKey;
}
