package com.alexander.controller.model.request;

import lombok.Data;

@Data
public class CreateTableRequestDTO {
    String name;
    String container;
    String database;
}
