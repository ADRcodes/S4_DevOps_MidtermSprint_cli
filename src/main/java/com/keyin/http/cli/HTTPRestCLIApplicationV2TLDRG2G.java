package com.keyin.http.cli;

import com.keyin.http.client.UserClient;
import com.keyin.domain.User;
import com.keyin.http.client.EventClient2;
import com.keyin.domain.Event;

import java.util.List;

public class HTTPRestCLIApplicationV2TLDRG2G {
    public static void main(String[] args) {
        // Base URL of your API (no trailing slash)
        String apiBase = "http://localhost:8080";
        UserClient userClient = new UserClient(apiBase);

        try {
            List<User> users = userClient.getAllUsers();
            System.out.println("Fetched " + users.size() + " users:");
            for (User u : users) {
                System.out.printf("• [%d] %s <%s>%n",
                        u.getId(), u.getName(), u.getEmail());
            }
        } catch (Exception e) {
            System.err.println("❌ Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }

        EventClient2 eventClient = new EventClient2(apiBase);

        try {
            // 1) All events
            List<Event> all = eventClient.getAllEvents();
            System.out.println("All events (" + all.size() + "):");
            all.forEach(e -> printEvent(e));

            // 2) Events at venue #2
            Long venueId = 2L;
            List<Event> byVenue = eventClient.getEventsByVenue(venueId);
            System.out.println("\nEvents at venue " + venueId + " (" + byVenue.size() + "):");
            byVenue.forEach(e -> printEvent(e));

            // 3) Events by organizer #1
            Long organizerId = 1L;
            List<Event> byOrg = eventClient.getEventsByOrganizer(organizerId);
            System.out.println("\nEvents by organizer " + organizerId + " (" + byOrg.size() + "):");
            byOrg.forEach(e -> printEvent(e));

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void printEvent(Event e) {
        System.out.printf("• [%d] %s by %s @ %s (capacity: %d, price: %.2f)%n",
                e.getId(), e.getTitle(), e.getOrganizer().getName(),
                e.getVenue().getName(), e.getCapacity(), e.getPrice());
    }
}
