package com.keyin.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        Event event = new Event();

        User organizer = new User(1L, "Alex Dev", "alex@example.com");
        Venue venue = new Venue(10L, "Dev Center", "456 Dev Lane", 100);

        event.setId(100L);
        event.setCompany("NTech");
        event.setTitle("Spring Boot Bootcamp");
        event.setDate(LocalDateTime.of(2025, 7, 30, 10, 0));
        event.setDescription("Learn Spring Boot in a day!");
        event.setPrice(new BigDecimal("199.99"));
        event.setCapacity(50);
        event.setOrganizer(organizer);
        event.setVenue(venue);

        assertEquals(100L, event.getId());
        assertEquals("NTech", event.getCompany());
        assertEquals("Spring Boot Bootcamp", event.getTitle());
        assertEquals(LocalDateTime.of(2025, 7, 30, 10, 0), event.getDate());
        assertEquals("Learn Spring Boot in a day!", event.getDescription());
        assertEquals(new BigDecimal("199.99"), event.getPrice());
        assertEquals(50, event.getCapacity());
        assertEquals(organizer, event.getOrganizer());
        assertEquals(venue, event.getVenue());
    }

    @Test
    public void testFullConstructor() {
        User organizer = new User(2L, "Abdul Dev", "abdul@example.com");
        Venue venue = new Venue(20L, "Innovation Hall", "789 Tech Ave", 200);

        Event event = new Event(
                200L,
                "TechZone",
                "Dev Ops workshop",
                LocalDateTime.of(2025, 8, 15, 14, 30),
                "Deep dive into GitHub workflows",
                new BigDecimal("299.50"),
                75,
                organizer,
                venue
        );

        assertEquals(200L, event.getId());
        assertEquals("TechZone", event.getCompany());
        assertEquals("Dev Ops workshop", event.getTitle());
        assertEquals(LocalDateTime.of(2025, 8, 15, 14, 30), event.getDate());
        assertEquals("Deep dive into GitHub workflows", event.getDescription());
        assertEquals(new BigDecimal("299.50"), event.getPrice());
        assertEquals(75, event.getCapacity());
        assertEquals(organizer, event.getOrganizer());
        assertEquals(venue, event.getVenue());
    }
}
