package com.alexander.controller.model.request;

import lombok.Data;

@Data
public class CreateDatabaseRequestDTO {
    String name;
    String container;
    String discordId;
}
