package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.request.event.CreateEventRequestDTO;
import com.shongon.backend.domain.dto.request.event.UpdateEventRequestDTO;
import com.shongon.backend.domain.dto.response.event.CreateEventResponseDTO;
import com.shongon.backend.domain.dto.response.event.GetEventDetailsResponseDTO;
import com.shongon.backend.domain.dto.response.event.ListEventResponseDTO;
import com.shongon.backend.domain.dto.response.event.UpdateEventResponseDTO;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.request.event.CreateEventRequest;
import com.shongon.backend.domain.request.event.UpdateEventRequest;
import com.shongon.backend.mapper.EventMapper;
import com.shongon.backend.service.blueprint.EventService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.shongon.backend.utils.JwtUtils.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {

    EventMapper eventMapper;
    EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO
    ) {

        CreateEventRequest createEventRequest = eventMapper.fromEventDTO(createEventRequestDTO);

        UUID userId = parseUserId(jwt); // organizerID

        Event createEvent = eventService.createEvent(userId, createEventRequest);

        CreateEventResponseDTO createEventResponseDTO = eventMapper.toDTO(createEvent);

        return new ResponseEntity<>(createEventResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ) {

        UUID userId = parseUserId(jwt);

        Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);

        return ResponseEntity.ok(
                events.map(eventMapper::toListEventResponseDTO)
        );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDTO> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){

        UUID userId = parseUserId(jwt);

        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDTO> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDTO updateEventRequestDTO)
    {

        UpdateEventRequest updateEventRequest = eventMapper.fromUpdateEventRequestDTO(updateEventRequestDTO);

        UUID userId = parseUserId(jwt);

        Event updatedEvent = eventService.updateEventForOrganizer(
                userId, eventId, updateEventRequest
        );

        UpdateEventResponseDTO updateEventResponseDTO = eventMapper.toUpdateEventResponseDTO(updatedEvent);

        return ResponseEntity.ok(updateEventResponseDTO);
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);

        eventService.deleteEventForOrganizer(userId, eventId);

        return ResponseEntity.noContent().build();
    }
}
