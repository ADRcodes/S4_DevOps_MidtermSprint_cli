package com.keyin.domain;

import java.util.Objects;

public class Venue {
    private long id;
    private String name;
    private String address;
    private int capacity;

    public Venue() {
    }

    public Venue(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Venue(long id, String name, String address, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Venue venue = (Venue) obj;
        return id == venue.id &&
                capacity == venue.capacity &&
                Objects.equals(name, venue.name) &&
                Objects.equals(address, venue.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, capacity);
    }

}
