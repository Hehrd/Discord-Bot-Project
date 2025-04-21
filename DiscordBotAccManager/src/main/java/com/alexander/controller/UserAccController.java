package com.alexander.controller;

import com.alexander.controller.model.request.AddKeyRequestDTO;
import com.alexander.service.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/acc")
@CrossOrigin
public class UserAccController {
    @Autowired
    private UserAccService userAccService;

//    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) throws Exception {
//        userAccService.signup(userDTO.getEmail(), userDTO.getPassword());
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body("Successfully signed up!");
//    }
//
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody String accessToken) throws Exception {
        userAccService.login(accessToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully logged in!");
    }

    @RequestMapping(value = "/apikeys/genkey", method = RequestMethod.POST)
    public ResponseEntity<String> genKey(@RequestHeader String stripeAuth) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully generated key!");
    }

    @RequestMapping(value = "/apikeys/addkey", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addKey(@RequestBody AddKeyRequestDTO addKeyRequestDTO) throws Exception {
        userAccService.addKey(addKeyRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully added key!");
    }

    @RequestMapping(value = "/hasKey/{discordId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> hasKey(@PathVariable String discordId) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userAccService.hasKey(discordId));
    }
}
