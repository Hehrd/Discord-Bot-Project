package com.alexander.service.data;

import com.alexander.controller.model.response.*;
import com.alexander.persistence.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class UserDataService {
    protected UserRepository userRepository;
    protected RestTemplate restTemplate;

    protected final String DISCORD_OAUTH_URL = "https://discord.com/api/users/@me";

    protected UserDataService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

//    public PagedResponse<TableResponseDTO> getTableData(String accessToken, , Pageable pageable) {}


    protected DiscordAccResponse getDiscordResponse(String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // Remove "Bearer " (length 7)
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscordAccResponse> responseEntity = restTemplate.exchange(DISCORD_OAUTH_URL, HttpMethod.GET, entity, DiscordAccResponse.class);
        return responseEntity.getBody();
    }
}
