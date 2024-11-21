package lee.io.ai.domain.auth.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lee.io.ai.domain.auth.properties.GoogleProperties;
import lee.io.ai.domain.auth.properties.KakaoProperties;
import lee.io.ai.global.util.OAuth2Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthClient {

    private final KakaoProperties kakaoProperties;
    private final GoogleProperties googleProperties;

    private final OAuth2Util oAuth2Util;

    public String getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        Map<String, String> body = new HashMap<>();
        body.put("client_id", kakaoProperties.getClientId());
        body.put("client_secret", kakaoProperties.getClientSecret());
        body.put("code", code);
        body.put("grant_type", "authorization_code");
        body.put("redirect_uri", kakaoProperties.getRedirectUri());
        return oAuth2Util.getAccessToken(kakaoProperties.getTokenUrl(), headers, body);
    }

    public Map getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                kakaoProperties.getUserInfoUrl(),
                HttpMethod.POST,
                request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", jsonNode.get("id").asText());
        userInfo.put("email", jsonNode.get("kakao_account").get("email").asText());

        return userInfo;
    }

    public String getGoogleAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", googleProperties.getClientId());
        body.put("client_secret", googleProperties.getClientSecret());
        body.put("code", code);
        body.put("grant_type", "authorization_code");
        body.put("redirect_uri", googleProperties.getRedirectUri());
        return oAuth2Util.getAccessToken(googleProperties.getTokenUrl(), headers, body);
    }

    public Map getGoogleUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(googleProperties.getUserInfoUrl(), HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

}