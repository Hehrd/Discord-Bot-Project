package com.alexander.persistence.model;

import com.alexander.controller.model.response.ContainerResponseDTO;
import com.alexander.controller.model.response.DatabaseResponseDTO;
import com.alexander.controller.model.response.TableResponseDTO;
import com.alexander.persistence.model.entities.UserContainerEntity;
import com.alexander.persistence.model.entities.UserDatabaseEntity;
import com.alexander.persistence.model.entities.UserTableEntity;

public class EntityMapper {
    public static ContainerResponseDTO toDTO(UserContainerEntity entity) {
        ContainerResponseDTO dto = new ContainerResponseDTO();
        dto.setName(entity.getName());
        return dto;
    }

    public static DatabaseResponseDTO toDTO(UserDatabaseEntity entity) {
        DatabaseResponseDTO dto = new DatabaseResponseDTO();
        dto.setName(entity.getName());
        dto.setContainerName(entity.getContainer().getName());
        return dto;
    }

    public static TableResponseDTO toDTO(UserTableEntity entity) {
        TableResponseDTO dto = new TableResponseDTO();
        dto.setName(entity.getName());
        dto.setSchemaJSON(entity.getSchemaJSON());
        dto.setDatabaseName(entity.getDatabase().getName());
        dto.setContainerName(entity.getDatabase().getContainer().getName());
        return dto;
    }
}
