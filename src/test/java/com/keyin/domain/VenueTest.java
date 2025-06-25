package com.keyin.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VenueTest {

    @Test
    public void testNoArgsConstructor() {
        Venue venue = new Venue();

        venue.setId(1L);
        venue.setName("Harbour Hall");
        venue.setAddress("123 NTech Street");
        venue.setCapacity(200);

        assertEquals(1L, venue.getId());
        assertEquals("Harbour Hall", venue.getName());
        assertEquals("123 NTech Street", venue.getAddress());
        assertEquals(200, venue.getCapacity());
    }

    @Test
    public void testConstructorWithoutId() {
        Venue venue = new Venue("Main Auditorium", "456 Water Ave", 300);

        assertEquals("Main Auditorium", venue.getName());
        assertEquals("456 Water Ave", venue.getAddress());
        assertEquals(300, venue.getCapacity());
    }

    @Test
    public void testFullConstructor() {
        Venue venue = new Venue(5L, "Error Spot", "404 Dev Blvd", 150);

        assertEquals(5L, venue.getId());
        assertEquals("Error Spot", venue.getName());
        assertEquals("404 Dev Blvd", venue.getAddress());
        assertEquals(150, venue.getCapacity());
    }
}
