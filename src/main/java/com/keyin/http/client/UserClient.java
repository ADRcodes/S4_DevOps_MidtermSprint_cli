package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.keyin.domain.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class UserClient {
    private String baseUrl;
    private HttpClient client;
    private final ObjectMapper mapper;

    public UserClient(String serverUrl) {
        // ensure it ends with "/api/users"
        if (serverUrl.endsWith("/")) {
            this.baseUrl = serverUrl + "api/users";
        } else {
            this.baseUrl = serverUrl + "/api/users";
        }
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /** GET all users */
    public List<User> getAllUsers() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch users: HTTP " + resp.statusCode());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<User>>() {});
    }

    /** GET one user by ID */
    public User getUserById(Long id) throws IOException, InterruptedException {
        String url = baseUrl + "/" + id;
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 404) {
            return null;
        } else if (resp.statusCode() != 200) {
            throw new RuntimeException("Error fetching user: HTTP " + resp.statusCode());
        }
        return mapper.readValue(resp.body(), User.class);
    }


}
