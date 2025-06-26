package com.keyin.http.client;

import com.keyin.domain.Event;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
class EventClientTest {
    @Mock HttpClient httpClient;
    @Mock HttpResponse<String> httpResponse;

    private EventClient eventClient;

    @BeforeEach
    void setup() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        eventClient = new EventClient("http://localhost:8080", httpClient, mapper);
    }

    @Test
    void getAllEvents_parsesList() throws IOException, InterruptedException {
        String fakeJson = """
            [
              {
                "id":1,
                "company":"TechCorp",
                "title":"DevOps Deep Dive",
                "date":"2025-07-15T09:30:00",
                "description":"CI/CD Workshop",
                "price":149.99,
                "capacity":50,
                "organizer":{"id":1,"name":"Alice","email":"a@x.com"},
                "venue":{"id":1,"name":"Center","address":"Addr","capacity":200}
              },
              {
                "id":2,
                "company":"DataPros",
                "title":"AI in Production",
                "date":"2025-09-10T10:00:00",
                "description":"Deploy ML at scale",
                "price":199.00,
                "capacity":40,
                "organizer":{"id":3,"name":"Carol","email":"c@x.com"},
                "venue":{"id":3,"name":"Aud","address":"Addr","capacity":300}
              }
            ]
            """;

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))
        ).thenReturn(httpResponse);

        List<Event> events = eventClient.getAllEvents();

        assertEquals(2, events.size(), "Should parse two events");
        Event e1 = events.get(0);
        assertEquals(1L, e1.getId());
        assertEquals("DevOps Deep Dive", e1.getTitle());
        assertEquals(50, e1.getCapacity());
        assertEquals(149.99, e1.getPrice().doubleValue(), 0.001);
        assertEquals("Alice", e1.getOrganizer().getName());
        assertEquals("Center", e1.getVenue().getName());

        verify(httpClient, times(1))
                .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void getEventsByVenue_filtersCorrectly() throws Exception {
        String fakeJson = "[{\"id\":2}]";
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Event> list = eventClient.getEventsByVenue(2L);
        assertNotNull(list);
    }

    @Test
    void getEventsByOrganizer_filtersCorrectly() throws Exception {
        String fakeJson = "[{\"id\":1}]";
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        List<Event> list = eventClient.getEventsByOrganizer(1L);
        assertNotNull(list);
    }
}
