package com.keyin.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    @Test
    public void testConstructorAndGetters() {
        User user = new User(1L, "Alice", "alice@example.com");
        Event event = new Event();
        event.setId(2L);
        event.setTitle("Sample Event");
        LocalDateTime now = LocalDateTime.of(2025, 7, 1, 12, 0);

        Registration reg = new Registration(user, event, now);
        reg.setId(3L);

        assertEquals(3L, reg.getId());
        assertSame(user, reg.getUser(),        "User instance should match");
        assertEquals("Alice", reg.getUser().getName());
        assertSame(event, reg.getEvent(),      "Event instance should match");
        assertEquals("Sample Event", reg.getEvent().getTitle());
        assertEquals(now, reg.getRegistrationDate());
    }

    @Test
    public void testSetters() {
        Registration reg = new Registration();

        reg.setId(4L);
        assertEquals(4L, reg.getId());

        User user = new User();
        user.setId(5L);
        user.setName("Bob");
        user.setEmail("bob@example.com");
        reg.setUser(user);
        assertEquals(5L, reg.getUser().getId());
        assertEquals("Bob", reg.getUser().getName());
        assertEquals("bob@example.com", reg.getUser().getEmail());

        Event event = new Event();
        event.setId(6L);
        event.setTitle("Another Event");
        reg.setEvent(event);
        assertEquals(6L, reg.getEvent().getId());
        assertEquals("Another Event", reg.getEvent().getTitle());

        LocalDateTime dt = LocalDateTime.of(2025, 7, 2, 15, 30);
        reg.setRegistrationDate(dt);
        assertEquals(dt, reg.getRegistrationDate());
    }
}
