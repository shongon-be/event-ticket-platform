package com.shongon.backend.domain.dto.request.ticket_validation;

import com.shongon.backend.domain.enums.TicketValidationMethod;
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
public class TicketValidationRequestDTO {
    UUID id; // qrcodeId || ticketId
    TicketValidationMethod method;
}
