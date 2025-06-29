package com.keyin.http.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationClientTest {

    @Mock HttpClient httpClient;
    @Mock HttpResponse<String> httpResponse;

    private RegistrationClient registrationClient;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        registrationClient = new RegistrationClient("http://localhost:8080", httpClient, mapper);
    }

    @Test
    void getAllRegistrations_parsesCorrectly() throws IOException, InterruptedException {
        String fakeJson = """
            [
              {
                "id": 1,
                "user": {"id": 1, "name": "John Doe", "email": "john@example.com"},
                "event": {
                  "id": 1,
                  "title": "Spring Boot Conf",
                  "company": "SpringOrg",
                  "dateTime": "2025-06-27T10:00:00",
                  "venue": {
                    "id": 1,
                    "name": "Main Hall",
                    "address": "123 Street",
                    "capacity": 200
                  }
                },
                "registrationDate": "2025-06-25T09:00:00"
              },
              {
                "id": 2,
                "user": {"id": 2, "name": "Jane Smith", "email": "jane@example.com"},
                "event": {
                  "id": 2,
                  "title": "Java Summit",
                  "company": "JavaOrg",
                  "dateTime": "2025-06-30T14:00:00",
                  "venue": {
                    "id": 2,
                    "name": "Grand Hall",
                    "address": "456 Avenue",
                    "capacity": 500
                  }
                },
                "registrationDate": "2025-06-26T12:00:00"
              }
            ]
            """;

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Registration> registrations = registrationClient.getAllRegistrations();

        assertEquals(2, registrations.size());
        assertEquals(1L, registrations.get(0).getId());
        assertEquals("John Doe", registrations.get(0).getUser().getName());
        assertEquals("Spring Boot Conf", registrations.get(0).getEvent().getTitle());
    }

    @Test
    void getAllRegistrations_emptyList() throws IOException, InterruptedException {
        String fakeJson = "[]";

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Registration> registrations = registrationClient.getAllRegistrations();

        assertNotNull(registrations);
        assertTrue(registrations.isEmpty());
    }

    @Test
    void getAllRegistrations_singleItem() throws IOException, InterruptedException {
        String fakeJson = """
            [
              {
                "id": 5,
                "user": {
                  "id": 1,
                  "name": "John Doe",
                  "email": "john@example.com"
                },
                "event": {
                  "id": 1,
                  "title": "Spring Boot Conf",
                  "company": "SpringOrg",
                  "dateTime": "2025-06-27T10:00:00",
                  "venue": {
                    "id": 1,
                    "name": "Main Hall",
                    "address": "123 Street",
                    "capacity": 200
                  }
                },
                "registrationDate": "2025-06-25T09:00:00"
              }
            ]
            """;

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Registration> registrations = registrationClient.getAllRegistrations();

        assertEquals(1, registrations.size());
        assertEquals("John Doe", registrations.get(0).getUser().getName());
        assertEquals("Spring Boot Conf", registrations.get(0).getEvent().getTitle());
    }
}
