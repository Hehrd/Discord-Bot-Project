package com.alexander.bot.model;

import lombok.Data;

@Data
public class AddKeyRequestDTO {
    private String apiKey;
    private String discordId;
}
