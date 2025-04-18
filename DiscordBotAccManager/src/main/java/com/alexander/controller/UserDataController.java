package com.alexander.controller;

import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.request.AuthenticatedUserRequestDTO;
import com.alexander.controller.model.response.ContainerResponseDTO;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/data")
@CrossOrigin(origins = "*")
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = "/containers/{discordId}", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse> getContainers(@PathVariable String discordId,
                                                       @RequestHeader("Authorization") String key,
                                                       Pageable pageable) {
        AuthenticatedUserRequestDTO authenticatedUserRequestDTO = new AuthenticatedUserRequestDTO();
        authenticatedUserRequestDTO.setDiscordId(discordId);
        authenticatedUserRequestDTO.setApiKey(key);
        PagedResponse<ContainerResponseDTO> containerPage = userDataService.getUserContainers(authenticatedUserRequestDTO, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(containerPage);
    }

    @RequestMapping(value = "/containers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createContainer(@RequestBody CreateContainerRequestDTO createContainerRequestDTO) {
        userDataService.addContainer(createContainerRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully created container!");
    }
}
