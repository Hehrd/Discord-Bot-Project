package com.alexander.persistence.repository;

import com.alexander.persistence.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MyRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
