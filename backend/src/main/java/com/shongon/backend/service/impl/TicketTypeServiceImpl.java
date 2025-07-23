package com.shongon.backend.service.impl;

import com.google.zxing.WriterException;
import com.shongon.backend.domain.entity.Ticket;
import com.shongon.backend.domain.entity.TicketType;
import com.shongon.backend.domain.entity.User;
import com.shongon.backend.domain.enums.TicketStatusEnum;
import com.shongon.backend.exception.TicketSoldOutException;
import com.shongon.backend.exception.TicketTypeNotFoundException;
import com.shongon.backend.exception.UserNotFoundException;
import com.shongon.backend.repository.TicketRepository;
import com.shongon.backend.repository.TicketTypeRepository;
import com.shongon.backend.repository.UserRepository;
import com.shongon.backend.service.blueprint.QrCodeService;
import com.shongon.backend.service.blueprint.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketTypeServiceImpl implements TicketTypeService {

    UserRepository userRepository;
    TicketRepository ticketRepository;
    TicketTypeRepository ticketTypeRepository;
    QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) throws WriterException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", userId)
                ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID '%s' not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);

        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
