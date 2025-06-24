package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Event;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class EventClient {
    private String serverURL;
    private final HttpClient client;

    public EventClient() {
        this.client = HttpClient.newHttpClient();
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/events"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                events = mapper.readValue(response.body(), new TypeReference<List<Event>>() {});
            } else {
                System.out.println("Failed to get events. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return events;
    }

    public Event getEventById(Long eventId) {
        Event event = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/events/" + eventId))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                event = mapper.readValue(response.body(), Event.class);
            } else {
                System.out.println("Failed to get event by ID. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return event;
    }
}
