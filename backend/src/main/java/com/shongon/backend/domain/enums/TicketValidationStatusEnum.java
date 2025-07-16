package com.shongon.backend.domain.enums;

public enum TicketValidationStatusEnum {
    VALID,
    INVALID, // stand for a QR code doesn't exist. (fake)
    EXPIRED
}
