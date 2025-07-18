package com.shongon.backend.domain.dto.response.ticket_type;

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
public class GetEventDetailsTiketTypesResponseDTO {
    UUID id;
    String name;
    Double price;
    String description;
    Integer totalAvailable;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
