package com.alexander.service;

import com.alexander.controller.model.UserCredentialsDTO;
import com.alexander.persistence.model.UserEntity;
import com.alexander.persistence.repository.UserRepository;
import com.alexander.tools.HashingTool;
import com.alexander.validation.validations.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final String HASH_ALGORITHM = "SHA-256";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    public void signup(String email, String password) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPasswordHash(HashingTool.hashString(password, HASH_ALGORITHM));

        validationService.validate(Validations.SIGNUP_VALIDATION, userEntity);

        userRepository.save(userEntity);
    }

    public void login(String email, String password) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPasswordHash(HashingTool.hashString(password, HASH_ALGORITHM));

        validationService.validate(Validations.LOGIN_VALIDATION, userEntity);
    }


}
