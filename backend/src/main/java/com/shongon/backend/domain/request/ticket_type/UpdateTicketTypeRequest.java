package com.shongon.backend.domain.request.ticket_type;

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
public class UpdateTicketTypeRequest {
    UUID id;
    String name;
    Double price;
    String description;
    Integer totalAvailable;
}
