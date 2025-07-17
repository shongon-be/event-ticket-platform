package com.shongon.backend.service.blueprint;

import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.request.CreateEventRequest;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
