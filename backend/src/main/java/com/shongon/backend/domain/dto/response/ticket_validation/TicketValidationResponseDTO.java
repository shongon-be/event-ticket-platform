package com.shongon.backend.domain.dto.response.ticket_validation;

import com.shongon.backend.domain.enums.TicketValidationStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketValidationResponseDTO {
    UUID ticketId;
    TicketValidationStatusEnum status;
}
