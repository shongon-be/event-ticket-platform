package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.response.event.GetPublishedEventDetailsResponseDTO;
import com.shongon.backend.domain.dto.response.event.ListPublishedEventResponseDTO;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.mapper.EventMapper;
import com.shongon.backend.service.blueprint.EventService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublishedEventController {

    EventService eventService;
    EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> listPublishedEvents(
            @RequestParam(required = false) String query,
            Pageable pageable
    ) {

        Page<Event> events;
        if(query != null && !query.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(query, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(
                events.map(eventMapper::toListPublishedEventResponseDTO)
        );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDTO> getPublishedEventDetails(
        @PathVariable UUID eventId
    ){

        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
