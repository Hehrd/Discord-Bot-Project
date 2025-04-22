package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_containers_table")
@Data
public class    UserContainerEntity extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserAccEntity user;

    @OneToMany(mappedBy = "container")
    private List<UserDatabaseEntity> databases;
}
