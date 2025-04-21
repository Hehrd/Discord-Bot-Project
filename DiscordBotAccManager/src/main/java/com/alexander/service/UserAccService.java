package com.alexander.service;

import com.alexander.controller.model.request.AddKeyRequestDTO;
import com.alexander.controller.model.response.DiscordAccResponse;
import com.alexander.persistence.model.entities.UserAccEntity;
import com.alexander.persistence.repository.ApiKeyRepository;
import com.alexander.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAccService {
    private final String HASH_ALGORITHM = "SHA-256";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ValidationService validationService;

//    public void signup(String email, String password) throws Exception {
//        UserAccEntity userAccEntity = new UserAccEntity();
//        userAccEntity.setEmail(email);
//        userAccEntity.setPasswordHash(HashingTool.hashString(password, HASH_ALGORITHM));
//
//        validationService.validate(Validations.SIGNUP_VALIDATION, userAccEntity);
//
//        userRepository.save(userAccEntity);
//    }

    public void login(String accessToken) throws Exception {
        String url = "https://discord.com/api/users/@me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<DiscordAccResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, DiscordAccResponse.class);
        UserAccEntity userAccEntity = new UserAccEntity();
        userAccEntity.setDiscordId(responseEntity.getBody().getId());
        if (!userRepository.existsByDiscordId(responseEntity.getBody().getId())) {
            userRepository.save(userAccEntity);
        }

    }

    public void addKey(AddKeyRequestDTO addKeyRequestDTO) throws Exception {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(addKeyRequestDTO.getDiscordId()).orElse(null);
        userAccEntity.setApiKey(apiKeyRepository.findByApiKey(addKeyRequestDTO.getApiKey()).orElse(null));
        userRepository.save(userAccEntity);
    }

    public boolean hasKey(String discordId) {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(discordId).orElse(null);
        if (userAccEntity == null || userAccEntity.getApiKey() == null) {
            return false;
        }
        return true;
    }


}
