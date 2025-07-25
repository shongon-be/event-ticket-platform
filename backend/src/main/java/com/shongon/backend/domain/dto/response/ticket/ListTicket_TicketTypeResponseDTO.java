package com.shongon.backend.domain.dto.response.ticket;

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
public class ListTicket_TicketTypeResponseDTO {
    UUID id;
    String name;
    Double price;
}
