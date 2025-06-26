package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Venue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class VenueClient {
    private String serverURL;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public VenueClient() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public VenueClient(String serverURL, HttpClient client, ObjectMapper mapper) {
        this.serverURL = serverURL;
        this.client = client;
        this.mapper = mapper;
    }

    public List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/venues"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Use the instance mapper instead of creating a new one
                venues = mapper.readValue(response.body(), new TypeReference<List<Venue>>() {});
            } else {
                System.out.println("Failed to get venues. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return venues;
    }

    public List<Venue> buildVenueListFromResponse(String jsonResponse) {
        List<Venue> venues = new ArrayList<>();
        try {
            // Use the instance mapper instead of creating a new one
            venues = mapper.readValue(jsonResponse, new TypeReference<List<Venue>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return venues;
    }
}