package com.alexander.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
}
