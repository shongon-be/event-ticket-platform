package com.shongon.backend.domain.request;

import com.shongon.backend.domain.enums.EventStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEventRequest {
    String name;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String venue;
    LocalDateTime salesStart;
    LocalDateTime salesEnd;
    EventStatusEnum status;
    List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
