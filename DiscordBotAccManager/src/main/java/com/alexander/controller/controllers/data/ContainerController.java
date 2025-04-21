package com.alexander.controller.controllers.data;

import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.response.ContainerResponseDTO;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.service.data.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/containers")
public class ContainerController {
    @Autowired
    private ContainerService containerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResponse> getContainers(@RequestHeader("Authorization") String accessToken,
                                                       Pageable pageable) {
//        AuthenticatedUserRequestDTO authenticatedUserRequestDTO = new AuthenticatedUserRequestDTO();
//        authenticatedUserRequestDTO.setDiscordId(discordId);
//        authenticatedUserRequestDTO.setApiKey(key);
        PagedResponse<ContainerResponseDTO> containerPage = containerService.getUserContainers(accessToken, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(containerPage);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createContainer(@RequestBody CreateContainerRequestDTO createContainerRequestDTO) {
        containerService.addContainer(createContainerRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully created container!");
    }
}
