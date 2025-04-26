package com.alexander.bot.dto.request;

import lombok.Data;

@Data
public class AuthenticatedUserRequestDTO {
    private String discordId;
    private String apiKey;
}
