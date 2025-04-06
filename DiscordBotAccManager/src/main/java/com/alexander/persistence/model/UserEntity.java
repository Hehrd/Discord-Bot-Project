package com.alexander.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_table")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String passwordHash;

    @Column(nullable = true, unique = true)
    private String apiToken;


}
