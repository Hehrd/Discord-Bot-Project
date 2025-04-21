package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.UserAccEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MyRepository<UserAccEntity> {
    boolean existsByDiscordId(String discordId);
//    boolean existsByEmailAndPasswordHash(String email, String passwordHash);

    Optional<UserAccEntity> findByDiscordId(String discordId);
}
