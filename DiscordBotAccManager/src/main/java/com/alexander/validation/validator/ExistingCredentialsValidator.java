package com.alexander.validation.validator;

import com.alexander.controller.model.ValidationData;
import com.alexander.exception.exceptions.EmailAlreadyInUseException;
import com.alexander.persistence.model.UserEntity;
import com.alexander.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistingCredentialsValidator extends Validator<UserRepository>{
    @Autowired
    public ExistingCredentialsValidator(UserRepository repository) {
        super(repository);
    }

    public void validate(ValidationData validationData) throws EmailAlreadyInUseException {
        UserEntity userEntity = (UserEntity) validationData.getValidationData(UserEntity.class);
        if (!repository.existsByEmailAndPasswordHash(userEntity.getEmail(), userEntity.getPasswordHash())) {
            throw new EmailAlreadyInUseException("Email already in use!");
        }
    }
}
