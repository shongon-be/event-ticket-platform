package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.request.ticket_validation.TicketValidationRequestDTO;
import com.shongon.backend.domain.dto.response.ticket_validation.TicketValidationResponseDTO;
import com.shongon.backend.domain.entity.TicketValidation;
import com.shongon.backend.domain.enums.TicketValidationMethod;
import com.shongon.backend.mapper.TicketValidationMapper;
import com.shongon.backend.service.blueprint.TicketValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketValidationController {

    TicketValidationService ticketValidationService;
    TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDTO> validateTicket(
            @RequestBody TicketValidationRequestDTO request
    ) {
        TicketValidation ticketValidation;

        TicketValidationMethod method = request.getMethod();

        if (TicketValidationMethod.MANUAL.equals(method)){
            ticketValidation = ticketValidationService.validateTicketManually(request.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(request.getId());
        }

        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDTO(ticketValidation)
        );
    }

}
