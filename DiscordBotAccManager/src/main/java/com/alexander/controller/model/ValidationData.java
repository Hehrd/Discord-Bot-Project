package com.alexander.controller.model;

import com.alexander.persistence.model.entities.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class ValidationData {
    private final Map<Class<? extends BaseEntity>, Object> validationData;

    public ValidationData() {
        validationData = new HashMap<>();
    }

    public Object getValidationData(Class<? extends BaseEntity> validationDataClass) {
        return validationData.get(validationDataClass);
    }

    public void putValidationData(Class<? extends BaseEntity> clazz, Object value) {
        validationData.put(clazz, value);
    }
}
