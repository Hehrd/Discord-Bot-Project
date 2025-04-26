package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_table")
public class UserAccEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String discordId;
//
//    @Column(nullable = false, unique = true)
//    private String passwordHash;

    @OneToOne
    @JoinColumn(nullable = true, unique = true, name = "apiKey", referencedColumnName = "id")
    private ApiKeyEntity apiKey;

}
