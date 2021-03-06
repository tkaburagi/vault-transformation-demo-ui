package run.kabuctl.vaulttokenizationdemoui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class UiService {

    private static final String host = "http://127.0.0.1:8080";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UiService(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        this.restTemplate = builder.build();
        this.objectMapper = objectMapper;
    }

    public User[] getTransformedUsers() throws Exception {
        String url = host;
        String result = this.restTemplate.getForObject(url + "/api/v1/get-transformed-users", String.class);

        System.out.println(result);
        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public User[] getSimpleTransformedUsers() throws Exception {
        String url = host;
        String result = this.restTemplate.getForObject(url + "/api/v1/get-simple-transformed-users", String.class);

        System.out.println(result);
        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }


    public User[] getSimplestTransformedUsers() throws Exception {
        String url = host;
        String result = this.restTemplate.getForObject(url + "/api/v1/get-simplest-transformed-users", String.class);

        System.out.println(result);
        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public User[] getEncryptedUsers() throws Exception {
        String url = host;
        String result = this.restTemplate.getForObject(url + "/api/v1/get-encrypted-users", String.class);

        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public void addOneEncryptedUser(String username, String password, String email, String creditcard, String howto) {
        String targetUrl = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> input = new LinkedHashMap<>();
        input.put("username", username);
        input.put("password", password);
        input.put("email", email);
        input.put("creditcard", creditcard);

        System.out.println("howto:" + howto);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(input, headers);

        targetUrl = new UiAppUtil().urlBuilder(host, howto, username, password, email, creditcard);
        this.restTemplate.postForObject(targetUrl, entity, String.class);
    }

    public String getOneDecryptedUser(String username) {
        String url = "http://127.0.0.1:8080";
        return this.restTemplate.getForObject(url + "/api/v1/decrypt?username=" + username, String.class);
    }

    public String getOneDecodedUser(String username, String flag) {
        String url = "http://127.0.0.1:8080";
        return this.restTemplate.getForObject(url + "/api/v1/decode?username=" + username +"&flag=" + flag, String.class);
    }
}