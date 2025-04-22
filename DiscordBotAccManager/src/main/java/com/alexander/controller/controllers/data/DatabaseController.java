package com.alexander.controller.controllers.data;

import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.request.CreateDatabaseRequestDTO;
import com.alexander.controller.model.response.DatabaseResponseDTO;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.service.data.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
>>>>>>> 6621e7c (commitche4)

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "/databases")
public class DatabaseController {
    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResponse> getDatabases(@RequestHeader("Authorization") String accessToken, Pageable pageable) {
        PagedResponse<DatabaseResponseDTO> databasePage = databaseService.getUserDatabases(accessToken, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(databasePage);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createContainer(@RequestBody CreateDatabaseRequestDTO createDatabaseRequestDTO) {
        databaseService.addDatabase(createDatabaseRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully created container!");
    }
}
