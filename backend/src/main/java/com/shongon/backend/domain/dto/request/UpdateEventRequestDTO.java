package com.shongon.backend.domain.dto.request;

import com.shongon.backend.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventRequestDTO {

    @NotNull(message = "Event ID must be provided")
    UUID id;

    @NotBlank(message = "Event name is required")
    String name;

    LocalDateTime start;

    LocalDateTime end;

    @NotBlank(message = "Venue information is required")
    String venue;

    LocalDateTime salesStart;

    LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided")
    EventStatusEnum status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    List<UpdateTicketTypeRequestDTO> ticketTypes;
}
