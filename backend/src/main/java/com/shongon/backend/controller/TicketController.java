package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.response.ticket.GetTicketDetailsResponseDTO;
import com.shongon.backend.domain.dto.response.ticket.ListTicketResponseDTO;
import com.shongon.backend.mapper.TicketMapper;
import com.shongon.backend.service.blueprint.QrCodeService;
import com.shongon.backend.service.blueprint.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

import static com.shongon.backend.utils.JwtUtils.parseUserId;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {

    TicketService ticketService;
    QrCodeService qrCodeService;
    TicketMapper ticketMapper;

    @GetMapping
    public Page<ListTicketResponseDTO> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return ticketService.listTicketsForPurchaser(parseUserId(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDTO);
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketDetailsResponseDTO> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {

        return ticketService
                .getTicketForPurchaser(parseUserId(jwt), ticketId)
                .map(ticketMapper::toGetTicketDetailsResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {

        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(
                parseUserId(jwt),
                ticketId
        );

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeImage);
    }

}
