package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.request.CreateEventRequestDTO;
import com.shongon.backend.domain.dto.request.CreateTicketTypeRequestDTO;
import com.shongon.backend.domain.dto.request.UpdateEventRequestDTO;
import com.shongon.backend.domain.dto.request.UpdateTicketTypeRequestDTO;
import com.shongon.backend.domain.dto.response.*;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.request.CreateEventRequest;
import com.shongon.backend.domain.request.CreateTicketTypeRequest;
import com.shongon.backend.domain.request.UpdateEventRequest;
import com.shongon.backend.domain.request.UpdateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

        // Create
    CreateTicketTypeRequest fromTicketTypeDTO(CreateTicketTypeRequestDTO dto);

    CreateEventRequest fromEventDTO(CreateEventRequestDTO dto);

    CreateEventResponseDTO toDTO(Event event);

        // List
    ListEventTicketTypeResponseDTO toDTO(TicketType ticketType);

    ListEventResponseDTO toListEventResponseDTO(Event event);

        // Get
    GetEventDetailsTiketTypesResponseDTO toGetEventDetailsTiketTypesResponseDTO(TicketType ticketType);

    GetEventDetailsResponseDTO toGetEventDetailsResponseDTO(Event event);

        // Update
    UpdateTicketTypeRequest fromUpdateTicketTypeRequestDTO(UpdateTicketTypeRequestDTO dto);

    UpdateEventRequest fromUpdateEventRequestDTO(UpdateEventRequestDTO dto);

    UpdateTicketTypeResponseDTO toUpdateTicketTypeResponseDTO(TicketType ticketType);

    UpdateEventResponseDTO toUpdateEventResponseDTO(Event event);
}
