package com.alexander.validation.validator;

import com.alexander.controller.model.ValidationData;
import com.alexander.persistence.repository.MyRepository;

public abstract class Validator<T extends MyRepository> {
    protected T repository;

    public Validator(T repository) {
        this.repository = repository;
    }

    public abstract void validate(ValidationData validationData) throws Exception;
}
