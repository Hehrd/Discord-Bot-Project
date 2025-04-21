package com.alexander.controller.model.response;

import lombok.Data;

@Data
public class TableResponseDTO {
    private String name;
    private String schemaJSON;
    private String databaseName;
    private String containerName;
}
