package com.keyin.http.client;

import com.keyin.domain.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientTest {
    @Mock HttpClient httpClient;
    @Mock HttpResponse<String> httpResponse;

    private UserClient userClient;

    @BeforeEach
    void setup() {
        ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        userClient = new UserClient("http://localhost:8080", httpClient, mapper);
    }

    @Test
    void getAllUsers_returnsParsedUsers() throws IOException, InterruptedException {
        String fakeJson = """
            [
              {"id":1, "name":"Alice Smith", "email":"alice@example.com"},
              {"id":2, "name":"Bob Johnson", "email":"bob.johnson@example.com"}
            ]
            """;

        // Stub the response
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body())      .thenReturn(fakeJson);

        // Stub the client.send(...) call (any request, any body handler)
        when(httpClient.send(
                any(HttpRequest.class),
                any(BodyHandler.class))
        ).thenReturn(httpResponse);

        List<User> users = userClient.getAllUsers();

        assertEquals(2, users.size());
        assertEquals(1L, users.get(0).getId());
        assertEquals("Alice Smith", users.get(0).getName());
        assertEquals("alice@example.com", users.get(0).getEmail());

        assertEquals(2L, users.get(1).getId());
        assertEquals("Bob Johnson", users.get(1).getName());
        assertEquals("bob.johnson@example.com", users.get(1).getEmail());

        // Ensure send(...) was invoked exactly once
        verify(httpClient, times(1))
                .send(any(HttpRequest.class), any(BodyHandler.class));
    }
}
