package com.alexander.persistence.repository;

import com.alexander.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MyRepository<UserEntity> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPasswordHash(String email, String passwordHash);
}
