package com.alexander.bot.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTableRequestDTO {
    private String container;
    private String database;
    private String name;
}
