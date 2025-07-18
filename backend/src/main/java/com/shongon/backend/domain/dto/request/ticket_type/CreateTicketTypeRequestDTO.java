package com.shongon.backend.domain.dto.request.ticket_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTicketTypeRequestDTO {

    @NotBlank(message = "Ticket type is required")
    String name;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be greater than or equal to zero")
    Double price;

    String description;

    Integer totalAvailable;
}
