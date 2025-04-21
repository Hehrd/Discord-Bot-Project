package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.UserContainerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContainerRepository extends MyRepository<UserContainerEntity>{
    Page<UserContainerEntity> findAllByUser_DiscordId(String discordId, Pageable pageable);
}
