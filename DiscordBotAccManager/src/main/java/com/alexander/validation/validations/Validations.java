package com.alexander.validation.validations;

import java.util.Arrays;
import java.util.List;

public enum Validations {
    SIGNUP_VALIDATION(Arrays.asList("uniqueEmailValidator")),
    LOGIN_VALIDATION(Arrays.asList("existingCredentialsValidator")),;

    private final List<String> validators;

    Validations(List<String> validators) {
        this.validators = validators;
    }

    public List<String> getValidators() {
        return validators;
    }
}
