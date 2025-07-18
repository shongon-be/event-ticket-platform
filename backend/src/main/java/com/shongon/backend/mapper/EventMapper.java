package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.request.CreateEventRequestDTO;
import com.shongon.backend.domain.dto.request.CreateTicketTypeRequestDTO;
import com.shongon.backend.domain.dto.response.CreateEventResponseDTO;
import com.shongon.backend.domain.dto.response.ListEventResponseDTO;
import com.shongon.backend.domain.dto.response.ListEventTicketTypeResponseDTO;
import com.shongon.backend.domain.entity.Event;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.request.CreateEventRequest;
import com.shongon.backend.domain.request.CreateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromTicketTypeDTO(CreateTicketTypeRequestDTO dto);

    CreateEventRequest fromEventDTO(CreateEventRequestDTO dto);

    CreateEventResponseDTO toDTO(Event event);

    ListEventTicketTypeResponseDTO toDTO(TicketType ticketType);

    ListEventResponseDTO toListEventResponseDTO(Event event);
}
