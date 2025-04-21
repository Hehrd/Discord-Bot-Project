package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.UserDatabaseEntity;
import com.alexander.persistence.model.entities.UserTableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserTableRepository extends MyRepository<UserTableEntity>{
    Page<UserTableEntity> findAllByUser_DiscordId(String discordId, Pageable pageable);
}
