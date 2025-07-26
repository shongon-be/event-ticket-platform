# Event Ticket Platform

## Project Brief

The **Event Ticket Platform** is a comprehensive application designed to empower event organizers to create and sell events, enable attendees to purchase tickets, and equip event staff to validate tickets via QR code scanning. The system supports the complete event management lifecycle, including event creation, ticket sales, real-time sales monitoring, and secure ticket validation at events. The platform is built for three primary user types:

- **Event Organizers:** Create, manage, and monitor events and ticket sales.
- **Event Attendees:** Discover events, purchase tickets, and access QR code tickets.
- **Event Staff:** Validate tickets and manage event entry.

## Tech Stack

**Backend:**
- **Spring Boot (Java):** REST API framework.
- **Spring Security:** Authentication and authorization.
- **OAuth2 & OpenID Connect:** Secure authentication protocols.
- **Keycloak:** Identity and access management.
- **PostgreSQL:** Relational database management system.

**Frontend:**
- **React (npm):** Modern frontend framework.  
  _Note: Frontend implementation and template are provided separately._

**Authentication/Authorization:**
- **Keycloak:** Centralized authentication and authorization for all user flows.

**Database:**
- **PostgreSQL:** Robust, scalable relational database.

## Architecture Design

The application architecture consists of these key components:

- **Spring Boot Application:** Backend REST APIs for all business logic and data management.
- **React Application:** Frontend client for organizers, attendees, and staff (template provided).
- **Keycloak:** Dedicated authentication server for user management, secure login, and role-based access.
- **PostgreSQL Database:** Persistent storage for events, tickets, users, and validations.

```
          +---------------------+
          |    React Frontend   |
          +----------+----------+
                     |
                     v
          +----------+----------+
          |    Spring Boot API  |
          +----------+----------+
                     |
          +----------+----------+
          |   PostgreSQL DB     |
          +---------------------+

          +---------------------+
          |     Keycloak        |
          +---------------------+
```

## System Design & REST API Design

The backend exposes a series of RESTful APIs for event management, ticket sales, and validation.

### Organizer Flow

- `POST /api/v1/events` - Create Event
- `GET /api/v1/events` - List Events
- `GET /api/v1/events/{event_id}` - Retrieve Event
- `PUT /api/v1/events/{event_id}` - Update Event
- `DELETE /api/v1/events/{event_id}` - Delete Event
- `GET /api/v1/events/{event_id}/tickets` - List Sold Tickets
- `GET /api/v1/events/{event_id}/tickets/{ticket_id}` - Retrieve Sold Ticket
- `PATCH /api/v1/events/{event_id}/tickets` - Partially Update Event
- `GET /api/v1/events/{event_id}/ticket-types` - List Ticket Types
- `GET /api/v1/events/{event_id}/ticket-types/{ticket_type_id}` - Retrieve Ticket Type
- `DELETE /api/v1/events/{event_id}/ticket-types/{ticket_type_id}` - Delete Ticket Type
- `PATCH /api/v1/events/{event_id}/ticket-types/{ticket_type_id}` - Partially Update Ticket Type

### Attendee Flow

- `GET /api/v1/published-events` - Search Published Events
- `GET /api/v1/published-event/{published_event_id}` - Retrieve Published Event
- `POST /api/v1/published-event/{published_event_id}/ticket-types/{ticket_type_id}` - Purchase Ticket
- `GET /api/v1/tickets` - List User Tickets
- `GET /api/v1/tickets/{ticket_id}` - Retrieve User Ticket
- `GET /api/v1/tickets/{ticket_id}/qr-codes` - Retrieve Ticket QR Code

### Staff Flow

- `POST /api/v1/events/{event_id}/ticket-validations` - Validate Ticket
- `GET /api/v1/events/{event_id}/ticket-validations` - List Ticket Validations

## Domain Modelling

### Key Domain Objects

- **Event:**  
  - `id`, `name`, `dateTime`, `venue`, `salesEndDate`
- **Ticket Type:**  
  - `id`, `name` (e.g., "VIP", "Standard"), `price`, `totalAvailable`
- **Ticket:**  
  - `id`, `status`, `createdDateTime`
- **QR Code:**  
  - `id`, `generatedDateTime`, `status`
- **User:**  
  - `id`, `name`, `email`, with roles: Organizer, Attendee, Staff
- **Ticket Validation:**  
  - `id`, `validationTime`, `validationMethod`, `status`

The domain model includes cardinality (e.g., an Event has many Ticket Types, a Ticket belongs to one Attendee), data types, and required vs. optional fields.

## User Journey

### Organizers

- **Corporate Event Manager:**  
  - Plans and manages events, monitors ticket sales, oversees event operations, and reviews post-event reports.
- **Event Planning Professional:**  
  - Sets up events, manages ticket types, and prepares event dashboards.
- **Part-Time Event Organizer:**  
  - Creates events, manages them via mobile, handles day-of-event setup, and oversees execution.

### Attendees

- **Busy Parent Event Attendee:**  
  - Searches for events, plans attendance, purchases tickets, and prepares for event day.
- **Young Event-Goer:**  
  - Discovers events, purchases tickets, attends, and engages post-event.
- **Corporate Networking Attendee:**  
  - Registers for networking events, attends, and follows up post-event.

### Staff

- **Event Staff Coordinator:**  
  - Manages pre-event setup, entry processes, and monitors ticket validation.
- **Entry-Level Event Staff:**  
  - Prepares for peak entry, handles ticket scanning, and resolves entry issues.

## User Interface Design

> **Note:** The frontend React client is provided as a separate template.  
> Below are the major UI flows:

- **Sign-up & Login:** Sign-up, confirmation, and login pages.
- **Organizer Flow:** Organizer dashboard, event creation/edit, event and sales dashboards, reporting.
- **Attendee Flow:** Event discovery, details, purchase, confirmation, personal ticket dashboard, ticket QR code viewing.
- **Staff Flow:** Event selection and ticket scanning.

---

## Getting Started (Backend)

### Prerequisites

- Java 17+
- Maven or Gradle
- PostgreSQL instance
- Keycloak server (configured with realms and clients for the platform)

### Running the Backend

1. **Configure `application.properties` for database and Keycloak settings.**
2. **Start PostgreSQL and Keycloak.**
3. **Build and run the Spring Boot application:**

   ```bash
   ./mvnw spring-boot:run
   # or
   ./gradlew bootRun
   ```

4. **APIs available at** `http://localhost:8080/api/v1/`

---
## Resources

Here are some valuable resources to help you get started and deepen your understanding of the backend technologies and architecture used in this project:

- **Spring Boot**
  - [Official Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
  - [Spring Boot Guides](https://spring.io/guides)
- **Spring Security**
  - [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
  - [Spring Security with OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- **Keycloak**
  - [Keycloak Documentation](https://www.keycloak.org/documentation)
  - [Securing Applications with Keycloak](https://www.keycloak.org/docs/latest/securing_apps/)
- **PostgreSQL**
  - [PostgreSQL Official Documentation](https://www.postgresql.org/docs/)
  - [PostgreSQL Tutorial](https://www.postgresqltutorial.com/)
- **RESTful API Design**
  - [REST API Tutorial](https://restfulapi.net/)
  - [Best Practices for REST API Design](https://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api)
- **OAuth2 & OpenID Connect**
  - [OAuth 2.0 and OpenID Connect (in plain English)](https://developer.okta.com/blog/2019/10/21/illustrated-guide-to-oauth-and-oidc)
  - [OAuth 2.0 Simplified](https://oauth.net/2/)
- [Build an Event Ticket Platform with Spring Boot](https://www.youtube.com/watch?v=vK-KQZ8cpbU)

If you'd like more learning, please consider exploring video tutorials and sample projects related to Spring Boot, Keycloak integration, and PostgreSQL best practices.

---

## License

This project is for educational purposes.
