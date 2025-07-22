package com.shongon.backend.repository;

import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query( value = "SELECT DISTINCT e FROM Event e" +
            " LEFT JOIN FETCH e.ticketTypes t" +
            " WHERE e.organizer.id = :organizerId",
            countQuery = "SELECT COUNT(DISTINCT e) FROM Event e" +
                    " WHERE e.organizer.id = :organizerId"
    )
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

    @EntityGraph(attributePaths = "ticketTypes")
    Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);

    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(value = "SELECT * FROM events WHERE " +
            "event_status = 'PUBLISHED' AND " +
            "to_tsvector('english', COALESCE(event_name, '') || ' ' || COALESCE(venue, '')) " +
            "@@ plainto_tsquery('english', :searchTerm)",
            countQuery = "SELECT count(*) FROM events WHERE " +
                    "event_status = 'PUBLISHED' AND " +
                    "to_tsvector('english', COALESCE(event_name, '') || ' ' || COALESCE(venue, '')) " +
                    "@@ plainto_tsquery('english', :searchTerm)",
            nativeQuery = true
    )
    Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

    @EntityGraph(attributePaths = "ticketTypes")
    Optional<Event> findByIdAndStatus(UUID id, EventStatusEnum status);
}
