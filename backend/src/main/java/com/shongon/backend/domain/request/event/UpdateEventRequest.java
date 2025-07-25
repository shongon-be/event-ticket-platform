package com.shongon.backend.domain.request.event;

import com.shongon.backend.domain.enums.EventStatusEnum;
import com.shongon.backend.domain.request.ticket_type.UpdateTicketTypeRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventRequest {
    UUID id;
    String name;
    LocalDateTime start;
    LocalDateTime end;
    String venue;
    LocalDateTime salesStart;
    LocalDateTime salesEnd;
    EventStatusEnum status;
    List<UpdateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
