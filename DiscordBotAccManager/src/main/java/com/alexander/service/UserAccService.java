package com.alexander.service;

import com.alexander.controller.model.request.ActivateKeyRequestDTO;
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
    private final String DISCORD_OAUTH_URL = "https://discord.com/api/users/@me";

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
        DiscordAccResponse discordAcc = getDiscordResponse(accessToken);
        if (!userRepository.existsByDiscordId(discordAcc.getId())) {
            UserAccEntity userAccEntity = new UserAccEntity();
            userAccEntity.setDiscordId(discordAcc.getId());
            userRepository.save(userAccEntity);
        }
    }

    public void activateKey(ActivateKeyRequestDTO activateKeyRequestDTO) throws Exception {
        DiscordAccResponse discordAcc = getDiscordResponse(activateKeyRequestDTO.getAccessToken());
        UserAccEntity userAccEntity = userRepository.findByDiscordId(discordAcc.getId()).orElse(null);
        userAccEntity.setApiKey(apiKeyRepository.findByApiKey(activateKeyRequestDTO.getApiKey()).orElse(null));
        userRepository.save(userAccEntity);
    }

    public boolean hasKey(String discordId) {
        UserAccEntity userAccEntity = userRepository.findByDiscordId(discordId).orElse(null);
        if (userAccEntity == null || userAccEntity.getApiKey() == null) {
            return false;
        }
        return true;
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
