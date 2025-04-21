package com.alexander.service;

import com.alexander.controller.model.request.AuthenticatedUserRequestDTO;
import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.response.ContainerResponseDTO;
import com.alexander.controller.model.response.DatabaseResponseDTO;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.controller.model.response.TableResponseDTO;
import com.alexander.persistence.model.EntityMapper;
import com.alexander.persistence.model.entities.UserAccEntity;
import com.alexander.persistence.model.entities.UserContainerEntity;
import com.alexander.persistence.model.entities.UserDatabaseEntity;
import com.alexander.persistence.model.entities.UserTableEntity;
import com.alexander.persistence.repository.UserContainerRepository;
import com.alexander.persistence.repository.UserDatabaseRepository;
import com.alexander.persistence.repository.UserRepository;
import com.alexander.persistence.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserContainerRepository userContainerRepository;
    @Autowired
    private UserDatabaseRepository userDatabaseRepository;
    @Autowired
    private UserTableRepository userTableRepository;

    public PagedResponse<ContainerResponseDTO> getUserContainers(AuthenticatedUserRequestDTO authenticatedUserRequestDTO, Pageable pageable) {
        String discordId = authenticatedUserRequestDTO.getDiscordId();
        Page<UserContainerEntity> page = userContainerRepository.findAllByUser_DiscordId(discordId, pageable);
        PagedResponse<ContainerResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public PagedResponse<DatabaseResponseDTO> getUserDatabases(AuthenticatedUserRequestDTO authenticatedUserRequestDTO, Pageable pageable) {
        String discordId = authenticatedUserRequestDTO.getDiscordId();
        Page<UserDatabaseEntity> page = userDatabaseRepository.findAllByUser_DiscordId(discordId, pageable);
        PagedResponse<DatabaseResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public PagedResponse<TableResponseDTO> getUserTables(AuthenticatedUserRequestDTO authenticatedUserRequestDTO, Pageable pageable) {
        String discordId = authenticatedUserRequestDTO.getDiscordId();
        Page<UserTableEntity> page = userTableRepository.findAllByUser_DiscordId(discordId, pageable);
        PagedResponse<TableResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public void addContainer(CreateContainerRequestDTO createContainerRequestDTO) {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(createContainerRequestDTO.getDiscordId()).orElse(null);
        UserContainerEntity userContainerEntity = new UserContainerEntity();
        userContainerEntity.setName(createContainerRequestDTO.getName());
        userContainerEntity.setUser(userAccEntity);
        userContainerRepository.save(userContainerEntity);
    }
}
