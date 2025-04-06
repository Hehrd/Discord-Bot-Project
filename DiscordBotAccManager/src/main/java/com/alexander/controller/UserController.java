package com.alexander.controller;

import com.alexander.controller.model.UserCredentialsDTO;
import com.alexander.persistence.model.UserEntity;
import com.alexander.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/discodb")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody UserCredentialsDTO userCredentialsDTO) throws Exception {
        userService.signup(userCredentialsDTO.getEmail(), userCredentialsDTO.getPassword());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully signed up!");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO userCredentialsDTO) throws Exception {
        userService.login(userCredentialsDTO.getEmail(), userCredentialsDTO.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully logged in!");
    }
}
