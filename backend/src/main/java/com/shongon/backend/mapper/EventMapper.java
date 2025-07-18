package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.request.event.CreateEventRequestDTO;
import com.shongon.backend.domain.dto.request.ticket_type.CreateTicketTypeRequestDTO;
import com.shongon.backend.domain.dto.request.event.UpdateEventRequestDTO;
import com.shongon.backend.domain.dto.request.ticket_type.UpdateTicketTypeRequestDTO;
import com.shongon.backend.domain.dto.response.event.CreateEventResponseDTO;
import com.shongon.backend.domain.dto.response.event.GetEventDetailsResponseDTO;
import com.shongon.backend.domain.dto.response.event.ListEventResponseDTO;
import com.shongon.backend.domain.dto.response.event.UpdateEventResponseDTO;
import com.shongon.backend.domain.dto.response.ticket_type.GetEventDetailsTiketTypesResponseDTO;
import com.shongon.backend.domain.dto.response.ticket_type.ListEventTicketTypeResponseDTO;
import com.shongon.backend.domain.dto.response.ticket_type.UpdateTicketTypeResponseDTO;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.request.event.CreateEventRequest;
import com.shongon.backend.domain.request.ticket_type.CreateTicketTypeRequest;
import com.shongon.backend.domain.request.event.UpdateEventRequest;
import com.shongon.backend.domain.request.ticket_type.UpdateTicketTypeRequest;
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
