package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.response.ticket.ListTicketResponseDTO;
import com.shongon.backend.domain.dto.response.ticket.ListTicket_TicketTypeResponseDTO;
import com.shongon.backend.domain.entity.Ticket;
import com.shongon.backend.domain.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicket_TicketTypeResponseDTO toListTicket_ticketTypeResponseDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO(Ticket ticket);

}
