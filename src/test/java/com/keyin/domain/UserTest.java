package com.keyin.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testConstructorAndGetters() {
        User user = new User(1L, "Alex", "Alex@example.com");

        assertEquals(1L, user.getId());
        assertEquals("Alex", user.getName());
        assertEquals("Alex@example.com", user.getEmail());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setId(2L);
        user.setName("Noah");
        user.setEmail("Noah@example.com");

        assertEquals(2L, user.getId());
        assertEquals("Noah", user.getName());
        assertEquals("Noah@example.com", user.getEmail());
    }

    @Test
    public void testToString() {
        User user = new User(3L, "Abdul", "abdul@example.com");

        String expected = "User [id=3, name=Abdul, email=abdul@example.com]";
        assertEquals(expected, user.toString());
    }
}
