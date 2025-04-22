package com.alexander.service.data;

import com.alexander.controller.model.response.DiscordAccResponse;
import com.alexander.controller.model.response.PagedResponse;
import com.alexander.controller.model.response.TableResponseDTO;
import com.alexander.persistence.model.EntityMapper;
import com.alexander.persistence.model.entities.UserTableEntity;
import com.alexander.persistence.repository.UserRepository;
import com.alexander.persistence.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TableService extends UserDataService {
    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    public TableService(UserRepository repository, RestTemplate restTemplate) {
        super(repository, restTemplate);
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
        String sql = String.format("SELECT * FROM %s LIMIT %d OFFSET %d", "user_table", pageable.getPageSize(), pageable.getOffset());
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-d %2$s -c \"%3$s\"",
                "test_aleks", "user_acc_db", sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        System.out.println(output.getBody());
        return output.getBody();
    }
}
