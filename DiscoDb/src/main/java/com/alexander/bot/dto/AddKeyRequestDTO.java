package com.alexander.bot.dto;

import lombok.Data;

@Data
public class AddKeyRequestDTO {
    private String apiKey;
    private String discordId;
}
