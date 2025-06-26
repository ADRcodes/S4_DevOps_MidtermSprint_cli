package com.keyin.http.client;

import com.keyin.domain.Venue;
import com.fasterxml.jackson.databind.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueClientTest {
    @Mock HttpClient httpClient;
    @Mock HttpResponse<String> httpResponse;

    private VenueClient venueClient;

    @BeforeEach
    void setup() {
        ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        venueClient = new VenueClient("http://localhost:8080", httpClient, mapper);
    }

    @Test
    void getAllVenues_parsesList() throws IOException, InterruptedException {
        String fakeJson = """
            [
              {
                "id": 1,
                "name": "Convention Center",
                "address": "123 Main St",
                "capacity": 500
              },
              {
                "id": 2,
                "name": "Community Hall",
                "address": "456 Oak Ave",
                "capacity": 200
              }
            ]
            """;

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))
        ).thenReturn(httpResponse);

        List<Venue> venues = venueClient.getAllVenues();

        assertEquals(2, venues.size(), "Should parse two venues");
        Venue v1 = venues.get(0);
        assertEquals(1L, v1.getId());
        assertEquals("Convention Center", v1.getName());
        assertEquals("123 Main St", v1.getAddress());
        assertEquals(500, v1.getCapacity());

        verify(httpClient, times(1))
                .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void getAllVenues_emptyList() throws Exception {
        String fakeJson = "[]";

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Venue> venues = venueClient.getAllVenues();

        assertEquals(0, venues.size());
        assertTrue(venues.isEmpty());
    }

    @Test
    void getAllVenues_singleVenue() throws Exception {
        String fakeJson = """
            [
              {
                "id": 3,
                "name": "Theater Hall",
                "address": "789 Park Ave",
                "capacity": 150
              }
            ]
            """;

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Venue> venues = venueClient.getAllVenues();

        assertEquals(1, venues.size());
        assertEquals("Theater Hall", venues.get(0).getName());
    }
}