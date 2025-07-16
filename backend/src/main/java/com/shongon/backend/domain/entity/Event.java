package com.shongon.backend.domain.entity;

import com.shongon.backend.domain.enums.EventStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @Column(name = "event_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "event_name", nullable = false)
    String name;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @Column(name = "venue", nullable = false)
    String venue;

    @Column(name = "sales_start")
    LocalDateTime salesStart;

    @Column(name = "sales_end")
    LocalDateTime salesEnd;

    @Column(name = "event_status", nullable = false)
    @Enumerated(EnumType.STRING)
    EventStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    User organizer;

    @ManyToMany(mappedBy = "attendingEvents")
    List<User> attendees = new ArrayList<>();

    @ManyToMany(mappedBy = "staffingEvents")
    List<User> staff = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    List<TicketType> ticketTypes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name) && Objects.equals(startDate, event.startDate) && Objects.equals(endDate, event.endDate) && Objects.equals(venue, event.venue) && Objects.equals(salesStart, event.salesStart) && Objects.equals(salesEnd, event.salesEnd) && status == event.status && Objects.equals(createdAt, event.createdAt) && Objects.equals(updatedAt, event.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, venue, salesStart, salesEnd, status, createdAt, updatedAt);
    }
}
