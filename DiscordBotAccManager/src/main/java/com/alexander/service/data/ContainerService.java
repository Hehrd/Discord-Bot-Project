package com.alexander.service.data;

import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.response.ContainerResponseDTO;
import com.alexander.controller.model.response.DiscordAccResponse;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.persistence.model.EntityMapper;
import com.alexander.persistence.model.entities.UserAccEntity;
import com.alexander.persistence.model.entities.UserContainerEntity;
import com.alexander.persistence.repository.UserContainerRepository;
import com.alexander.persistence.repository.UserRepository;
import com.alexander.validation.JWTService;
import com.auth0.jwt.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContainerService extends UserDataService {
    @Autowired
    private JWTService jwtService;


    @Autowired
    private UserContainerRepository userContainerRepository;

    @Autowired
    protected ContainerService(UserRepository repository, RestTemplate restTemplate) {
        super(repository, restTemplate);
    }

    public PagedResponse<ContainerResponseDTO> getUserContainers(String accessToken, Pageable pageable) {
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        Page<UserContainerEntity> page = userContainerRepository.findAllByUser_DiscordId(discordAcc.getId(), pageable);
        PagedResponse<ContainerResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public void addContainer(String containerName, String jwt) {
        String discordId = jwtService.getDiscordId(jwt);
        UserAccEntity userAccEntity = userRepository.findByDiscordId(discordId).orElse(null);
        UserContainerEntity userContainerEntity = new UserContainerEntity();
        userContainerEntity.setName(containerName);
        userContainerEntity.setUser(userAccEntity);
        userContainerRepository.save(userContainerEntity);
    }




}
