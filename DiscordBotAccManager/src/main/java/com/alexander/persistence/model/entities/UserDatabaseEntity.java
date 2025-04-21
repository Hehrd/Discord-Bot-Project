package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_databases_table")
@Data
public class UserDatabaseEntity extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "containerId", referencedColumnName = "id")
    private UserContainerEntity container;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserAccEntity user;

    @OneToMany(mappedBy = "database")
    private List<UserTableEntity> tables;
}
