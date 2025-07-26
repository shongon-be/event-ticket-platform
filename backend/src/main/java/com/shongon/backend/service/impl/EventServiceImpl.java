package com.shongon.backend.service.impl;

import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.entity.User;
import com.shongon.backend.domain.enums.EventStatusEnum;
import com.shongon.backend.domain.request.event.CreateEventRequest;
import com.shongon.backend.domain.request.event.UpdateEventRequest;
import com.shongon.backend.domain.request.ticket_type.CreateTicketTypeRequest;
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
    public Event createEvent(UUID userId, CreateEventRequest request) {
        User organizer = findUserById(userId);
        Event event = buildEventFromRequest(request, organizer);
        return eventRepository.save(event);
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
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest request) {
        validateUpdateRequest(eventId, request);

        Event existingEvent = findEventByIdAndOrganizer(eventId, organizerId);
        updateEventBasicInfo(existingEvent, request);
        updateEventTicketTypes(existingEvent, request.getTicketTypes());

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
        validateEventExists(eventId);
        getEventForOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);
    }

    // PublishedEventController
    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId, EventStatusEnum.PUBLISHED);
    }


    // Private helper methods
    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", userId)
                ));
    }

    private Event buildEventFromRequest(CreateEventRequest request, User organizer) {
        Event event = new Event();
        setEventBasicInfo(event, request, organizer);

        List<TicketType> ticketTypes = createTicketTypesForNewEvent(request, event);
        event.setTicketTypes(ticketTypes);

        return event;
    }

        private void setEventBasicInfo(Event event, CreateEventRequest request, User organizer) {
            event.setName(request.getName());
            event.setStart(request.getStartDate());
            event.setEnd(request.getEndDate());
            event.setVenue(request.getVenue());
            event.setSalesStart(request.getSalesStart());
            event.setSalesEnd(request.getSalesEnd());
            event.setStatus(request.getStatus());
            event.setOrganizer(organizer);
        }

        private List<TicketType> createTicketTypesForNewEvent(CreateEventRequest request, Event event) {
            return request.getTicketTypes()
                    .stream()
                    .map(ticketTypeRequest ->
                            createTicketType(ticketTypeRequest, event)
                    )
                    .toList();
        }

        private TicketType createTicketType(CreateTicketTypeRequest ticketTypeRequest, Event event) {
            TicketType ticketType = new TicketType();
            ticketType.setName(ticketTypeRequest.getName());
            ticketType.setPrice(ticketTypeRequest.getPrice());
            ticketType.setDescription(ticketTypeRequest.getDescription());
            ticketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
            ticketType.setEvent(event);
            return ticketType;
        }

    private static void validateUpdateRequest(UUID eventId, UpdateEventRequest request) {
        if (request.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if (!eventId.equals(request.getId())) {
            throw new EventUpdateException("Cannot update the ID of an event");
        }
    }

    private Event findEventByIdAndOrganizer(UUID eventId, UUID organizerId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with ID '%s' does not exist", eventId)
                ));
    }

    private void updateEventBasicInfo(Event event, UpdateEventRequest request) {
        event.setName(request.getName());
        event.setStart(request.getStart());
        event.setEnd(request.getEnd());
        event.setVenue(request.getVenue());
        event.setSalesStart(request.getSalesStart());
        event.setSalesEnd(request.getSalesEnd());
        event.setStatus(request.getStatus());
    }

    private void updateEventTicketTypes(Event existingEvent, List<UpdateTicketTypeRequest> ticketTypeRequests) {
        // Lấy danh sách ID của ticket types từ request
        Set<UUID> requestTicketTypeIds = getTicketTypeIdsFromRequest(ticketTypeRequests);

        // Xóa các ticket types không có trong request
        removeObsoleteTicketTypes(existingEvent, requestTicketTypeIds);

        // Tạo map để tra cứu nhanh existing ticket types
        Map<UUID, TicketType> existingTicketTypesMap = createTicketTypeMap(existingEvent);

        // Xử lý từng ticket type trong request
        processTicketTypeRequests(existingEvent, ticketTypeRequests, existingTicketTypesMap);
    }

        private Set<UUID> getTicketTypeIdsFromRequest(List<UpdateTicketTypeRequest> ticketTypeRequests) {
            return ticketTypeRequests.stream()
                    .map(UpdateTicketTypeRequest::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        private void removeObsoleteTicketTypes(Event event, Set<UUID> requestTicketTypeIds) {
            event.getTicketTypes().removeIf(ticketType ->
                    !requestTicketTypeIds.contains(ticketType.getId())
            );
        }

        private Map<UUID, TicketType> createTicketTypeMap(Event event) {
            return event.getTicketTypes().stream()
                    .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        }

        private void processTicketTypeRequests(Event event,
                                               List<UpdateTicketTypeRequest> requests,
                                               Map<UUID, TicketType> existingMap) {
            for (UpdateTicketTypeRequest request : requests) {
                if (request.getId() == null) {
                    // Tạo mới ticket type
                    createAndAddNewTicketType(event, request);
                } else if (existingMap.containsKey(request.getId())) {
                    // Cập nhật ticket type có sẵn
                    updateExistingTicketType(existingMap.get(request.getId()), request);
                } else {
                    // Ticket type không tồn tại
                    throw new TicketTypeNotFoundException(
                            String.format("Ticket type with ID '%s' does not exist", request.getId())
                    );
                }
            }
        }

            private void createAndAddNewTicketType(Event event, UpdateTicketTypeRequest request) {
                TicketType newTicketType = new TicketType();
                updateTicketTypeFields(newTicketType, request);
                newTicketType.setEvent(event);
                event.getTicketTypes().add(newTicketType);
            }

            private void updateExistingTicketType(TicketType ticketType, UpdateTicketTypeRequest request) {
                updateTicketTypeFields(ticketType, request);
            }

                private void updateTicketTypeFields(TicketType ticketType, UpdateTicketTypeRequest request) {
                    ticketType.setName(request.getName());
                    ticketType.setPrice(request.getPrice());
                    ticketType.setDescription(request.getDescription());
                    ticketType.setTotalAvailable(request.getTotalAvailable());
                }

    private void validateEventExists(UUID eventId) {
        if(eventRepository.findById(eventId).isEmpty()){
            throw new EventNotFoundException(
                    String.format("Event with ID '%s' not found", eventId)
            );
        }
    }
}
