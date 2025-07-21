package com.shongon.backend.domain.dto.response.event;

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
public class ListPublishedEventResponseDTO {
    UUID id;
    String name;
    LocalDateTime start;
    LocalDateTime end;
    String venue;
}
