package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_tables_table")
@Data
public class UserTableEntity extends BaseEntity {
    @Column
    private String name;

    @Column
    private String schemaJSON;

    @ManyToOne
    private UserDatabaseEntity database;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserAccEntity user;
}
