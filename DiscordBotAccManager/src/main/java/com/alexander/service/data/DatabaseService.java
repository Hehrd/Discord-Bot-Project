package com.alexander.service.data;

import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.request.CreateDatabaseRequestDTO;
import com.alexander.controller.model.response.DatabaseResponseDTO;
import com.alexander.controller.model.response.DiscordAccResponse;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.persistence.model.EntityMapper;
import com.alexander.persistence.model.entities.UserAccEntity;
import com.alexander.persistence.model.entities.UserContainerEntity;
import com.alexander.persistence.model.entities.UserDatabaseEntity;
import com.alexander.persistence.repository.UserContainerRepository;
import com.alexander.persistence.repository.UserDatabaseRepository;
import com.alexander.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DatabaseService extends UserDataService {
    @Autowired
    private UserDatabaseRepository userDatabaseRepository;
    @Autowired
    UserContainerRepository userContainerRepository;

    @Autowired
    protected DatabaseService(UserRepository userRepository, RestTemplate restTemplate) {
        super(userRepository, restTemplate);
    }

    public PagedResponse<DatabaseResponseDTO> getUserDatabases(String accessToken, Pageable pageable) {
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        Page<UserDatabaseEntity> page = userDatabaseRepository.findAllByUser_DiscordId(discordAcc.getId(), pageable);
        PagedResponse<DatabaseResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public void addDatabase(CreateDatabaseRequestDTO createDatabaseRequestDTO) {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(createDatabaseRequestDTO.getDiscordId()).orElse(null);
        UserDatabaseEntity userDatabaseEntity = new UserDatabaseEntity();
        userDatabaseEntity.setName(createDatabaseRequestDTO.getName());
        userDatabaseEntity.setUser(userAccEntity);
        userDatabaseEntity.setContainer(userContainerRepository.findByUser_DiscordIdAndName(createDatabaseRequestDTO.getDiscordId(), createDatabaseRequestDTO.getContainer()));
        userDatabaseRepository.save(userDatabaseEntity);
    }
}
