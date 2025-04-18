package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.ApiKeyEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends MyRepository<ApiKeyEntity>{
    Optional<ApiKeyEntity> findByApiKey(String apiKey);
}
