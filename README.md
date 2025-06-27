# Event Management CLI

## Overview

This is a Java command-line application that consumes the REST API and allows you to:

- List all users, events, and venues
- See upcoming events in the next 30 days
- Check what events a user is registered for
- See who's registered for each event
- See events grouped by venue

---

## How to Run

1. **Make sure the API server is running first!**

   - Start your Spring Boot API on `localhost:8080`.

2. **Clone and navigate**

   ```bash
   git clone <your-repo-url>
   cd S4_DevOps_MidtermSprint_cli
   ```

3. **Build & run**
   ```bash
   ./mvnw compile
   ./mvnw exec:java -Dexec.mainClass="com.keyin.http.cli.HTTPRestCLIApplication"
   ```
   Or simply run it from your IDE.

---

## Usage Example

When it starts, you'll see:

```
=== Event Management CLI ===
1. What events are happening in the next 7 days?
2. What events is a particular attendee registered for?
3. What events are being held at each venue?
4. Who has registered for each event?
5. Show all users
6. Show all events
7. Show all venues
0. Exit
```

Follow the prompts to explore your data.

---

## Notes

- If you see errors like `Failed to fetch users` or `Connection refused`, double-check that the API server is running and available at `http://localhost:8080`.
- This CLI assumes your API is running locally. If your server runs on a different port or host, change the `serverURLBase` in `HTTPRestCLIApplication`.
- If you are running the NTech API, a default data set will be added!

---

## Enjoy!

This CLI is a simple way to demo your full stack, and to test your API without Postman.
