package com.keyin.http.cli;

import com.keyin.domain.Event;
import com.keyin.domain.Registration;
import com.keyin.domain.User;
import com.keyin.domain.Venue;
import com.keyin.http.client.EventClient;
import com.keyin.http.client.RegistrationClient;
import com.keyin.http.client.VenueClient;

import java.time.LocalDateTime;
import java.util.*;

public class HTTPRestCLIApplication {

    private EventClient eventClient;
    private RegistrationClient registrationClient;
    private VenueClient venueClient;

    public static void main(String[] args) {
        HTTPRestCLIApplication app = new HTTPRestCLIApplication();

        String serverURL = args.length > 0 ? args[0] : "http://localhost:8080/api";
        app.initializeClients(serverURL);

        // For testing: call the functions here
        app.listUpcomingEvents();
        app.listEventsForUser(1); // Replace with real user ID for test
        app.listEventsByVenue();
        app.listUsersByEvent();
    }

    private void initializeClients(String serverURL) {
        eventClient = new EventClient();
        eventClient.setServerURL(serverURL);

        registrationClient = new RegistrationClient();
        registrationClient.setServerURL(serverURL);

        venueClient = new VenueClient();
        venueClient.setServerURL(serverURL);
    }


    public void listUpcomingEvents() {
        List<Event> events = eventClient.getAllEvents();
        LocalDateTime today = LocalDateTime.now();

        System.out.println("=== Events Happening in the Next 7 Days ===");

        for (Event event : events) {
            if (!event.getDate().isBefore(today) && !event.getDate().isAfter(today.plusDays(7))) {
                System.out.println("- " + event.getTitle() + " on " + event.getDate() + " at " + event.getVenue().getName());
            }
        }
    }

    public void listEventsForUser(long userId) {
        List<Registration> registrations = registrationClient.getAllRegistrations();

        System.out.println("=== Events Registered by User ID: " + userId + " ===");

        for (Registration reg : registrations) {
            if (reg.getUser().getId() == userId) {
                Event event = reg.getEvent();
                System.out.println("- " + event.getTitle() + " on " + event.getDate() + " at " + event.getVenue().getName());
            }
        }
    }

    public void listEventsByVenue() {
        List<Event> events = eventClient.getAllEvents();
        Map<String, List<Event>> venueMap = new HashMap<>();

        for (Event event : events) {
            String venueName = event.getVenue().getName();
            venueMap.computeIfAbsent(venueName, k -> new ArrayList<>()).add(event);
        }

        System.out.println("=== Events Grouped by Venue ===");
        for (Map.Entry<String, List<Event>> entry : venueMap.entrySet()) {
            System.out.println("Venue: " + entry.getKey());
            for (Event event : entry.getValue()) {
                System.out.println(" - " + event.getTitle() + " on " + event.getDate());
            }
        }
    }

    public void listUsersByEvent() {
        List<Registration> registrations = registrationClient.getAllRegistrations();
        Map<Long, List<User>> eventUserMap = new HashMap<>();

        for (Registration reg : registrations) {
            Long eventId = reg.getEvent().getId();
            eventUserMap.computeIfAbsent(eventId, k -> new ArrayList<>()).add(reg.getUser());
        }

        System.out.println("=== Users Registered per Event ===");

        for (Map.Entry<Long, List<User>> entry : eventUserMap.entrySet()) {
            Long eventId = entry.getKey();
            Event event = eventClient.getEventById(eventId); // Revisit: Be sure to include this method in EventClient

            System.out.println("Event: " + event.getTitle());
            for (User user : entry.getValue()) {
                System.out.println(" - " + user.getName() + " (" + user.getEmail() + ")");
            }
        }
    }
}
