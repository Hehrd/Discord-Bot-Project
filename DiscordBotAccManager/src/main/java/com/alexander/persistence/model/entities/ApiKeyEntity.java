package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "api_key_table")
@Data
public class ApiKeyEntity extends BaseEntity {
    @Column
    private String apiKey;
}
