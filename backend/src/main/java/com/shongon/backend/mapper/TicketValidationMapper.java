package com.shongon.backend.mapper;

import com.shongon.backend.domain.dto.response.ticket_validation.TicketValidationResponseDTO;
import com.shongon.backend.domain.entity.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDTO toTicketValidationResponseDTO(TicketValidation ticketValidation);
}
