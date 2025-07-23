package com.shongon.backend.service.blueprint;

import com.google.zxing.WriterException;
import com.shongon.backend.domain.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId) throws WriterException;
}
