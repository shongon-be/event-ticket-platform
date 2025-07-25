package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.response.ticket.GetTicketDetailsResponseDTO;
import com.shongon.backend.domain.dto.response.ticket.ListTicketResponseDTO;
import com.shongon.backend.domain.dto.response.ticket.ListTicket_TicketTypeResponseDTO;
import com.shongon.backend.domain.entity.Ticket;
import com.shongon.backend.domain.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
         // list all tickets
    ListTicket_TicketTypeResponseDTO toListTicket_ticketTypeResponseDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO(Ticket ticket);

        // get specific ticket details
    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    GetTicketDetailsResponseDTO toGetTicketDetailsResponseDTO(Ticket ticket);


}
