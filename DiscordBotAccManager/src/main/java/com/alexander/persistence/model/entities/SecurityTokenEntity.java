package com.alexander.persistence.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "api_key_table")
@Data
public class SecurityTokenEntity extends BaseEntity {
    @Column
    private String securityToken;

    @OneToOne
    @JoinColumn(nullable = true, unique = true, name = "user", referencedColumnName = "id")
    private UserAccEntity user;
}
