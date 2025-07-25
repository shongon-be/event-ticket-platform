package com.shongon.backend.domain.dto.response.ticket;

import com.shongon.backend.domain.enums.TicketStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTicketDetailsResponseDTO {
    UUID id;
    TicketStatusEnum status;
    Double price;
    String description;
    String eventName;
    String eventVenue;
    LocalDateTime eventStart;
    LocalDateTime eventEnd;
}
