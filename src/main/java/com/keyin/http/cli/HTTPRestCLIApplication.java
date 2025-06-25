package com.keyin.http.cli;

import com.keyin.domain.Event;
import com.keyin.domain.Registration;
import com.keyin.domain.User;
import com.keyin.domain.Venue;
import com.keyin.http.client.EventClient;
import com.keyin.http.client.RegistrationClient;
import com.keyin.http.client.UserClient;
import com.keyin.http.client.VenueClient;

import java.time.LocalDateTime;
import java.util.*;

public class HTTPRestCLIApplication {

//    private EventClient eventClient;
//    private RegistrationClient registrationClient;
//    private VenueClient venueClient;


    public static void main(String[] args) {
//        HTTPRestCLIApplication app = new HTTPRestCLIApplication();

        String serverURLBase = args.length > 0 ? args[0] : "http://localhost:8080";

        UserClient userClient   = new UserClient(serverURLBase);
        EventClient eventClient = new EventClient(serverURLBase);

//        app.initializeClients(serverURL);

        // For testing: call the functions here
//        app.listUpcomingEvents();
//        app.listEventsForUser(1); // Replace with real user ID for test
//        app.listEventsByVenue();
//        app.listUsersByEvent();

        // ─────── CLI Placeholder ─────────
        // TODO: wire up your Scanner/menu here.
        // Example calls you can uncomment to test:
         fetchAndPrintAllUsers(userClient);
         fetchAndPrintAllEvents(eventClient);
         fetchAndPrintEventsByVenue(eventClient, 2L);
         fetchAndPrintEventsByOrganizer(eventClient, 1L);
        // ─────────────────────────────────
    }

//    private void initializeClients(String serverURL) {
//        eventClient = new EventClient();
//        eventClient.setServerURL(serverURL);
//
//        registrationClient = new RegistrationClient();
//        registrationClient.setServerURL(serverURL);
//
//        venueClient = new VenueClient();
//        venueClient.setServerURL(serverURL);
//    }

    // ─────── User Flows ─────────
    private static void fetchAndPrintAllUsers(UserClient userClient) {
        try {
            List<User> users = userClient.getAllUsers();
            System.out.println("\nFetched " + users.size() + " users:");
            users.forEach(HTTPRestCLIApplication::printUser);
        } catch (Exception e) {
            System.err.println("❌ Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ─────── Event Flows ─────────
    private static void fetchAndPrintAllEvents(EventClient eventClient) {
        try {
            List<Event> events = eventClient.getAllEvents();
            System.out.println("\nAll events (" + events.size() + "):");
            events.forEach(HTTPRestCLIApplication::printEvent);
        } catch (Exception e) {
            System.err.println("❌ Error fetching events: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public void listEventsForUser(long userId) {
//        List<Registration> registrations = registrationClient.getAllRegistrations();
//
//        System.out.println("=== Events Registered by User ID: " + userId + " ===");
//
//        for (Registration reg : registrations) {
//            if (reg.getUser().getId() == userId) {
//                Event event = reg.getEvent();
//                System.out.println("- " + event.getTitle() + " on " + event.getDate() + " at " + event.getVenue().getName());
//            }
//        }
//    }

    private static void fetchAndPrintEventsByVenue(EventClient eventClient, Long venueId) {
        try {
            List<Event> events = eventClient.getEventsByVenue(venueId);
            System.out.println("\nEvents at venue " + venueId + " (" + events.size() + "):");
            events.forEach(HTTPRestCLIApplication::printEvent);
        } catch (Exception e) {
            System.err.println("❌ Error fetching events by venue: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public void listEventsByVenue() {
//        List<Event> events = eventClient.getAllEvents();
//        Map<String, List<Event>> venueMap = new HashMap<>();
//
//        for (Event event : events) {
//            String venueName = event.getVenue().getName();
//            venueMap.computeIfAbsent(venueName, k -> new ArrayList<>()).add(event);
//        }
//
//        System.out.println("=== Events Grouped by Venue ===");
//        for (Map.Entry<String, List<Event>> entry : venueMap.entrySet()) {
//            System.out.println("Venue: " + entry.getKey());
//            for (Event event : entry.getValue()) {
//                System.out.println(" - " + event.getTitle() + " on " + event.getDate());
//            }
//        }
//    }

    private static void fetchAndPrintEventsByOrganizer(EventClient eventClient, Long organizerId) {
        try {
            List<Event> events = eventClient.getEventsByOrganizer(organizerId);
            System.out.println("\nEvents by organizer " + organizerId + " (" + events.size() + "):");
            events.forEach(HTTPRestCLIApplication::printEvent);
        } catch (Exception e) {
            System.err.println("❌ Error fetching events by organizer: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public void listUsersByEvent() {
//        List<Registration> registrations = registrationClient.getAllRegistrations();
//        Map<Long, List<User>> eventUserMap = new HashMap<>();
//
//        for (Registration reg : registrations) {
//            Long eventId = reg.getEvent().getId();
//            eventUserMap.computeIfAbsent(eventId, k -> new ArrayList<>()).add(reg.getUser());
//        }
//
//        System.out.println("=== Users Registered per Event ===");
//
//        for (Map.Entry<Long, List<User>> entry : eventUserMap.entrySet()) {
//            Long eventId = entry.getKey();
//            Event event = eventClient.getEventById(eventId); // Revisit: Be sure to include this method in EventClient
//
//            System.out.println("Event: " + event.getTitle());
//            for (User user : entry.getValue()) {
//                System.out.println(" - " + user.getName() + " (" + user.getEmail() + ")");
//            }
//        }
//    }

    // ─────── Registration Flows ─────────



    // ─────── Venue Flows ─────────




    // ─────── Print Utilities ─────────
    private static void printUser(User u) {
        System.out.printf("• [%d] %s <%s>%n",
                u.getId(), u.getName(), u.getEmail());
    }

    private static void printEvent(Event e) {
        System.out.printf("• [%d] %s by %s @ %s (capacity: %d, price: %.2f)%n",
                e.getId(),
                e.getTitle(),
                e.getOrganizer().getName(),
                e.getVenue().getName(),
                e.getCapacity(),
                e.getPrice()
        );
    }
}
