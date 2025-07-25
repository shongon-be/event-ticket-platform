package com.shongon.backend.service.blueprint;

import com.shongon.backend.domain.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {
    Page<Ticket> listTicketsForPurchaser(UUID purchaserId, Pageable pageable);
}
