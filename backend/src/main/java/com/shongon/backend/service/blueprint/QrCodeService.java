package com.shongon.backend.service.blueprint;

import com.google.zxing.WriterException;
import com.shongon.backend.domain.entity.QrCode;
import com.shongon.backend.domain.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket) throws WriterException;
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);

}
