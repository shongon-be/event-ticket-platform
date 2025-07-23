package com.shongon.backend.controller;

import com.shongon.backend.domain.dto.response.ErrorResponseDTO;
import com.shongon.backend.exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketSoldOutException.class)
    public ResponseEntity<ErrorResponseDTO> handleTicketSoldOutException(TicketSoldOutException ex) {
        log.error("Caught TicketSoldOutException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("Ticket are sold out for this ticket type");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleQrCodeNotFoundException(QrCodeNotFoundException ex) {
        log.error("Caught QrCodeNotFoundException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("QR code not found");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<ErrorResponseDTO> handleQrCodeGenerationException(QrCodeGenerationException ex) {
        log.error("Caught QrCodeGenerationException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("Unable to generate QR code");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventUpdateException(EventUpdateException ex) {
        log.error("Caught EventUpdateException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("Unable to update event");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
        log.error("Caught TicketTypeNotFoundException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("Ticket type not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventNotFoundException(EventNotFoundException ex) {
        log.error("Caught EventNotFoundException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("Event not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("Caught UserNotFoundException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        errorDto.setError("User not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Caught Exception: ", e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setError("An unknown error occurred.");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Caught ConstraintViolationException: ", e);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

        String errorMessage = e.getConstraintViolations().stream()
                .findFirst()
                .map(violation ->
                        violation.getPropertyPath() + ": " + violation.getMessage()
                ).orElse("Constraint violation occurred.");

        errorResponseDTO.setError(errorMessage);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        log.error("Caught MethodArgumentNotValidException", ex);
        ErrorResponseDTO errorDto = new ErrorResponseDTO();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation error occurred");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
