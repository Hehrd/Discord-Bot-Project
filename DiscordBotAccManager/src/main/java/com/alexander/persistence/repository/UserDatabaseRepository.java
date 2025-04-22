package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.UserDatabaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDatabaseRepository extends MyRepository<UserDatabaseEntity>{
    Page<UserDatabaseEntity> findAllByUser_DiscordId(String discordId, Pageable pageable);
    UserDatabaseEntity findByUser_DiscordIdAndName(String discordId, String name);
}
