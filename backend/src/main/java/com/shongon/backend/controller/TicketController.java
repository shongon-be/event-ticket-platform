package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.response.ticket.ListTicketResponseDTO;
import com.shongon.backend.mapper.TicketMapper;
import com.shongon.backend.service.blueprint.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.shongon.backend.utils.JwtUtils.parseUserId;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {

    TicketService ticketService;
    TicketMapper ticketMapper;

    @GetMapping
    public Page<ListTicketResponseDTO> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable){
        return ticketService.listTicketsForPurchaser(parseUserId(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDTO);
    }
}
