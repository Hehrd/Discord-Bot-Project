package com.alexander.persistence.repository;

import com.alexander.persistence.model.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
