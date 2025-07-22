package com.shongon.backend.domain.dto.response.event;

import com.shongon.backend.domain.dto.response.ticket_type.GetEventDetailsTiketTypesResponseDTO;
import com.shongon.backend.domain.dto.response.ticket_type.GetPublishedEventDetailsTiketTypesResponseDTO;
import com.shongon.backend.domain.enums.EventStatusEnum;
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
public class GetPublishedEventDetailsResponseDTO {
    UUID id;
    String name;
    LocalDateTime start;
    LocalDateTime end;
    String venue;
    List<GetPublishedEventDetailsTiketTypesResponseDTO> ticketTypes = new ArrayList<>();
}
