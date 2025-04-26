package com.alexander.bot.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDatabaseRequestDTO {
    String container;
    String name;
}
