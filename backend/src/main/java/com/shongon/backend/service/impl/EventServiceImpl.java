package com.shongon.backend.service.impl;

import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.entity.User;
import com.shongon.backend.domain.enums.EventStatusEnum;
import com.shongon.backend.domain.request.event.CreateEventRequest;
import com.shongon.backend.domain.request.event.UpdateEventRequest;
import com.shongon.backend.domain.request.ticket_type.UpdateTicketTypeRequest;
import com.shongon.backend.exception.EventNotFoundException;
import com.shongon.backend.exception.EventUpdateException;
import com.shongon.backend.exception.TicketTypeNotFoundException;
import com.shongon.backend.exception.UserNotFoundException;
import com.shongon.backend.repository.EventRepository;
import com.shongon.backend.repository.UserRepository;
import com.shongon.backend.service.blueprint.EventService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {

    UserRepository userRepository;
    EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)
                ));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }
        ).toList();


        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStartDate());
        eventToCreate.setEnd(event.getEndDate());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event) {
        if (event.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if (!eventId.equals(event.getId())) {
            throw new EventUpdateException("Cannot update the ID of an event");
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with ID '%s' does not exist", eventId))
                );

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        Set<UUID> requestTicketTypeIds = event.getTicketTypes().stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (ticketType.getId() == null) {
                // Create
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);

            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
                // Update
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            } else {
                throw new TicketTypeNotFoundException(
                        String.format("Ticket type with ID '%s' does not exist", ticketType.getId())
                );
            }
        }

            return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
        if(eventRepository.findById(eventId).isEmpty()){
            throw new EventNotFoundException(
                    String.format("Event with ID '%s' not found", eventId)
            );
        }

        getEventForOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }
}
