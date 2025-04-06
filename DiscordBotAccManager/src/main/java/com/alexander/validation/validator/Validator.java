package com.alexander.validation.validator;

import com.alexander.controller.model.ValidationData;
import com.alexander.exception.exceptions.EmailAlreadyInUseException;
import com.alexander.persistence.model.UserEntity;
import com.alexander.persistence.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public abstract class Validator<T extends MyRepository> {
    protected T repository;

    public Validator(T repository) {
        this.repository = repository;
    }

    public abstract void validate(ValidationData validationData) throws Exception;
}
