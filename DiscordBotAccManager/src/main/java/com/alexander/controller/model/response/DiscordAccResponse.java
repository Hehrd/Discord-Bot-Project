package com.alexander.controller.model.response;

import lombok.Data;

@Data
public class DiscordAccResponse {
    private String id;
    private String username;
    private String discriminator;
    private String avatar;
}
