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

    public static void main(String[] args) {

        String serverURLBase = args.length > 0 ? args[0] : "http://localhost:8080";

        UserClient userClient   = new UserClient(serverURLBase);
        EventClient eventClient = new EventClient(serverURLBase);
        RegistrationClient registrationClient = new RegistrationClient();
        registrationClient.setServerURL(serverURLBase);


        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Event Management CLI ===");
            System.out.println("1. What events are happening in the next 7 days?");
            System.out.println("2. What events is a particular attendee registered for?");
            System.out.println("3. What events are being held at each venue?");
            System.out.println("4. Who has registered for each event?");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> listUpcomingEvents(eventClient);
                case 2 -> {
                    System.out.print("Enter user ID: ");
                    long userId = scanner.nextLong();
                    listEventsForUser(registrationClient, userId);
                }
                case 3 -> listEventsByVenue(eventClient);
                case 4 -> listUsersByEvent(registrationClient, eventClient);
                case 0 -> System.out.println(" Goodbye!");
                default -> System.out.println(" Invalid choice.");
            }
        } while (choice != 0);

        // Example calls you can uncomment to test:
         fetchAndPrintAllUsers(userClient);
         fetchAndPrintAllEvents(eventClient);
         fetchAndPrintEventsByVenue(eventClient, 2L);
         fetchAndPrintEventsByOrganizer(eventClient, 1L);
        // ─────────────────────────────────
    }
    private static void listUpcomingEvents(EventClient eventClient) {
        try {
            List<Event> events = eventClient.getAllEvents();
            System.out.println("\n=== Events Happening in Next 7 Days ===");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekLater = now.plusDays(7);

            for (Event event : events) {
                if (event.getDate().isAfter(now) && event.getDate().isBefore(oneWeekLater)) {
                    printEvent(event);
                }
            }
        } catch (Exception e) {
            System.err.println(" Error listing upcoming events: " + e.getMessage());
        }
    }

    private static void listEventsForUser(RegistrationClient registrationClient, long userId) {
        try {
            List<Registration> registrations = registrationClient.getAllRegistrations();
            System.out.println("=== Events Registered by User ID: " + userId + " ===");

            for (Registration reg : registrations) {
                if (reg.getUser().getId() == userId) {
                    Event event = reg.getEvent();
                    System.out.println("- " + event.getTitle() + " on " + event.getDate() + " at " + event.getVenue().getName());
                }
            }
        } catch (Exception e) {
            System.err.println(" Error listing user’s events: " + e.getMessage());
        }
    }

    private static void listEventsByVenue(EventClient eventClient) {
        try {
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
        } catch (Exception e) {
            System.err.println(" Error listing events by venue: " + e.getMessage());
        }
    }

    private static void listUsersByEvent(RegistrationClient registrationClient, EventClient eventClient) {
        try {
            List<Registration> registrations = registrationClient.getAllRegistrations();
            Map<Long, List<User>> eventUserMap = new HashMap<>();

            for (Registration reg : registrations) {
                Long eventId = reg.getEvent().getId();
                eventUserMap.computeIfAbsent(eventId, k -> new ArrayList<>()).add(reg.getUser());
            }

            System.out.println("=== Users Registered per Event ===");
            for (Map.Entry<Long, List<User>> entry : eventUserMap.entrySet()) {
                Event event = eventClient.getAllEvents().stream()
                        .filter(e -> e.getId().equals(entry.getKey()))
                        .findFirst()
                        .orElse(null);

                System.out.println("Event: " + event.getTitle());
                for (User user : entry.getValue()) {
                    System.out.println(" - " + user.getName() + " (" + user.getEmail() + ")");
                }
            }
        } catch (Exception e) {
            System.err.println(" Error listing users by event: " + e.getMessage());
        }

    }

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
