package com.keyin.domain;

import com.keyin.domain.Event;
import com.keyin.domain.User;
import java.time.LocalDateTime;

public class Registration {
    private Long id;
    private User user;
    private Event event;
    private LocalDateTime registrationDate;

    // Constructors
    public Registration() {}

    public Registration(User user, Event event, LocalDateTime registrationDate) {
        this.user = user;
        this.event = event;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}
