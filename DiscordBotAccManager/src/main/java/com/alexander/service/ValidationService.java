package com.alexander.service;

import com.alexander.Application;
import com.alexander.controller.model.DTO;
import com.alexander.controller.model.ValidationData;
import com.alexander.persistence.model.BaseEntity;
import com.alexander.validation.validations.Validations;
import com.alexander.validation.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    private Map<String, Validator> validators;

    @Autowired
    public ValidationService(ApplicationContext applicationContext) {
        validators = applicationContext.getBeansOfType(Validator.class);
    }

    public void validate(Validations validation, BaseEntity... entities) throws Exception {
        ValidationData validationData = new ValidationData();
        for (BaseEntity entity : entities) {
            validationData.putValidationData(entity.getClass(), entity);
        }
        for (String validatorName : validation.getValidators()) {
            Validator validator = validators.get(validatorName);
            validator.validate(validationData);
        }
    }
}
