package com.alexander.validation.validator;

import com.alexander.controller.model.ValidationData;
import com.alexander.exception.exceptions.EmailAlreadyInUseException;
import com.alexander.persistence.model.entities.UserAccEntity;
import com.alexander.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator extends Validator<UserRepository>{
    @Autowired
    public UniqueEmailValidator(UserRepository repository) {
        super(repository);
    }

    public void validate(ValidationData validationData) throws EmailAlreadyInUseException {
//        UserAccEntity userAccEntity = (UserAccEntity) validationData.getValidationData(UserAccEntity.class);
//        if (repository.existsByEmail(userAccEntity.getEmail())) {
//            throw new EmailAlreadyInUseException("Email already in use!");
//        }
    }
}
