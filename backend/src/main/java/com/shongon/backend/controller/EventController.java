package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.request.CreateEventRequestDTO;
import com.shongon.backend.domain.dto.response.CreateEventResponseDTO;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.request.CreateEventRequest;
import com.shongon.backend.mapper.EventMapper;
import com.shongon.backend.service.blueprint.EventService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

        UUID userId = UUID.fromString(jwt.getSubject()); // organizerID

        Event createEvent = eventService.createEvent(userId, createEventRequest);

        CreateEventResponseDTO createEventResponseDTO = eventMapper.toDTO(createEvent);

        return new ResponseEntity<>(createEventResponseDTO, HttpStatus.CREATED);

    }
}
