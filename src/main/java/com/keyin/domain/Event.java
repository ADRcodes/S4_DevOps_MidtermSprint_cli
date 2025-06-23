package com.keyin.domain;

import com.keyin.domain.User;
import com.keyin.domain.Venue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String company;
    private String title;
    private LocalDateTime date;
    private String description;
    private BigDecimal price;
    private int capacity;
    private User organizer;
    private Venue venue;

    // ─── Constructors ──────────────────────────────────────────────────────────
    public Event() {
        // Default constructor
    }

    public Event(Long id, String company, String title, LocalDateTime date, String description, BigDecimal price, int capacity, User organizer, Venue venue) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.date = date;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.organizer = organizer;
        this.venue = venue;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

}
