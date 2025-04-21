package com.alexander.service.data;

import com.alexander.controller.model.response.DatabaseResponseDTO;
import com.alexander.controller.model.response.DiscordAccResponse;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.persistence.model.EntityMapper;
import com.alexander.persistence.model.entities.UserDatabaseEntity;
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
}
