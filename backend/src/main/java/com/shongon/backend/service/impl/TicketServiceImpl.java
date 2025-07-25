package com.shongon.backend.service.impl;

import com.shongon.backend.domain.entity.Ticket;
import com.shongon.backend.repository.TicketRepository;
import com.shongon.backend.service.blueprint.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketsForPurchaser(UUID purchaserId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(purchaserId,pageable);
    }

    @Override
    public Optional<Ticket> getTicketForPurchaser(UUID purchaserId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, purchaserId);
    }
}
