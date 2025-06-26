package com.keyin.http.client;

import com.keyin.domain.Venue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VenueClientTest {

    @Test
    public void testGetAllVenues() throws Exception {
        String jsonResponse = "[\n{\n\"id\": 1,\n\"name\": \"Convention Center\",\n\"address\": \"123 Main St\",\n\"capacity\": 500\n},\n" +
                "{\n\"id\": 2,\n\"name\": \"Community Hall\",\n\"address\": \"456 Oak Ave\",\n\"capacity\": 200\n}\n]";

        VenueClient venueClientUnderTest = new VenueClient();

        List<Venue> venueList = venueClientUnderTest.buildVenueListFromResponse(jsonResponse);

        Assertions.assertEquals(2, venueList.size());
        Assertions.assertTrue(venueList.contains(new Venue(1, "Convention Center", "123 Main St", 500)));
        Assertions.assertTrue(venueList.contains(new Venue(2, "Community Hall", "456 Oak Ave", 200)));
    }


    @Test
    public void testGetAllVenuesEmptyList() throws Exception {
        String jsonResponse = "[]";

        VenueClient venueClientUnderTest = new VenueClient();
        List<Venue> venueList = venueClientUnderTest.buildVenueListFromResponse(jsonResponse);

        Assertions.assertEquals(0, venueList.size());
        Assertions.assertTrue(venueList.isEmpty());
    }

    @Test
    public void testGetAllVenuesSingleVenue() throws Exception {
        String jsonResponse = "[\n{\n\"id\": 3,\n\"name\": \"Theater Hall\",\n\"address\": \"789 Park Ave\",\n\"capacity\": 150\n}\n]";

        VenueClient venueClientUnderTest = new VenueClient();
        List<Venue> venueList = venueClientUnderTest.buildVenueListFromResponse(jsonResponse);

        Assertions.assertEquals(1, venueList.size());
        Assertions.assertTrue(venueList.contains(new Venue(3, "Theater Hall", "789 Park Ave", 150)));
    }
}