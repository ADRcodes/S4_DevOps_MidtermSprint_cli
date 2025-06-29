package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Registration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RegistrationClient {
    private String serverURL;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public RegistrationClient() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public RegistrationClient(String serverURL, HttpClient client, ObjectMapper mapper) {
        this.serverURL = serverURL;
        this.client = client;
        this.mapper = mapper;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/registrations"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                registrations = mapper.readValue(response.body(), new TypeReference<List<Registration>>() {});
            } else {
                System.out.println("Failed to get registrations. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return registrations;
    }

    public Registration getRegistrationById(Long registrationId) {
        Registration registration = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/registrations/" + registrationId))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                registration = mapper.readValue(response.body(), Registration.class);
            } else {
                System.out.println("Failed to get registration by ID. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return registration;
    }
}
