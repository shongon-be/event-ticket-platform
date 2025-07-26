package com.shongon.backend.service.impl;

import com.shongon.backend.domain.entity.QrCode;
import com.shongon.backend.domain.entity.Ticket;
import com.shongon.backend.domain.entity.TicketValidation;
import com.shongon.backend.domain.enums.QrCodeStatusEnum;
import com.shongon.backend.domain.enums.TicketValidationMethod;
import com.shongon.backend.domain.enums.TicketValidationStatusEnum;
import com.shongon.backend.exception.QrCodeNotFoundException;
import com.shongon.backend.exception.TicketNotFoundException;
import com.shongon.backend.repository.QrCodeRepository;
import com.shongon.backend.repository.TicketRepository;
import com.shongon.backend.repository.TicketValidationRepository;
import com.shongon.backend.service.blueprint.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketValidationServiceImpl implements TicketValidationService {

    QrCodeRepository qrCodeRepository;
    TicketValidationRepository ticketValidationRepository;
    TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format("QR code with ID '%s' not found", qrCodeId)
                ));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod method) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(method);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations()
                .stream()
                .filter(validation ->
                        TicketValidationStatusEnum.VALID.equals(validation.getStatus())
                )
                .findFirst()
                .map(validation -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }
}
