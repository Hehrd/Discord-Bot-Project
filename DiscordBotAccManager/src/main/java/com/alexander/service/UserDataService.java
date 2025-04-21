package com.alexander.service;

import com.alexander.controller.model.request.AuthenticatedUserRequestDTO;
import com.alexander.controller.model.request.CreateContainerRequestDTO;
import com.alexander.controller.model.response.*;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    private final String DISCORD_OAUTH_URL = "https://discord.com/api/users/@me";

    public PagedResponse<ContainerResponseDTO> getUserContainers(String accessToken, Pageable pageable) {
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        Page<UserContainerEntity> page = userContainerRepository.findAllByUser_DiscordId(discordAcc.getId(), pageable);
        PagedResponse<ContainerResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
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

    public PagedResponse<TableResponseDTO> getUserTables(String accessToken, Pageable pageable) {
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        Page<UserTableEntity> page = userTableRepository.findAllByUser_DiscordId(discordAcc.getId(), pageable);
        PagedResponse<TableResponseDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setContent(page.getContent().stream().map(entity -> EntityMapper.toDTO(entity)).toList());
        return pagedResponse;
    }

    public String getRows(String accessToken,
                                         String container,
                                         String database,
                                         String table,
                                         Pageable pageable) {
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        String containerName = container + "-" + discordAcc.getId();
        String sql = String.format("SELECT * FROM %s LIMIT %d OFFSET %d", table, pageable.getPageSize(), pageable.getOffset());
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-d %2$s -c \"%3$s\"",
                containerName, database, sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        return output.getBody();
    }

//    public PagedResponse<TableResponseDTO> getTableData(String accessToken, , Pageable pageable) {}
    public void addContainer(CreateContainerRequestDTO createContainerRequestDTO) {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(createContainerRequestDTO.getDiscordId()).orElse(null);
        UserContainerEntity userContainerEntity = new UserContainerEntity();
        userContainerEntity.setName(createContainerRequestDTO.getName());
        userContainerEntity.setUser(userAccEntity);
        userContainerRepository.save(userContainerEntity);
    }

    private DiscordAccResponse getDiscordResponse(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscordAccResponse> responseEntity = restTemplate.exchange(DISCORD_OAUTH_URL, HttpMethod.POST, entity, DiscordAccResponse.class);
        return responseEntity.getBody();
    }
}
