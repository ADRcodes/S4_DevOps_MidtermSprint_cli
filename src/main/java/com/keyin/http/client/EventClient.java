package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.keyin.domain.Event;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class EventClient {

    private final String baseUrl;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public EventClient(String serverUrl) {

        this.baseUrl = serverUrl + "/api/events";
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper()
                // Register the Java 8 date/time module
                .registerModule(new JavaTimeModule())
                // Write/read dates as ISO strings, not timestamps
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // Ignore any JSON props you’re not modeling
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    // package-private, only for tests
    EventClient(String serverUrl, HttpClient client, ObjectMapper mapper) {
        this.baseUrl = serverUrl + "/api/events";
        this.client = client;
        this.mapper = mapper;
    }

    public List<Event> getAllEvents() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch events: HTTP " + resp.statusCode());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Event>>() {});
    }

    public List<Event> getEventsByVenue(Long venueId) throws IOException, InterruptedException {
        String url = baseUrl + "?venueId=" + venueId;
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch events by venue: HTTP " + resp.statusCode());

        }
        return mapper.readValue(resp.body(), new TypeReference<List<Event>>() {});
    }

    public List<Event> getEventsByOrganizer(Long organizerId) throws IOException, InterruptedException {
        String url = baseUrl + "?organizerId=" + organizerId;
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch events by organizer: HTTP " + resp.statusCode());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Event>>() {});
    }
}
