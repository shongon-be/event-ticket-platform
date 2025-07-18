package com.shongon.backend.repository;

import com.shongon.backend.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query(value = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.ticketTypes t WHERE e.organizer.id = :organizerId",
            countQuery = "SELECT COUNT(DISTINCT e) FROM Event e WHERE e.organizer.id = :organizerId")
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

    @EntityGraph(attributePaths = "ticketTypes")
    Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);
}
