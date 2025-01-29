package com.msticket.ms_ticket_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.exceptions.ErrorMessage;
import com.msticket.ms_ticket_manager.services.impl.TicketServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "ms-ticket-management", description = "API for managing tickets.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private final TicketServiceImpl ticketServiceImpl;

    @Operation(summary = "Create a new ticket.", responses = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "422", description = "Invalid data provided", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDto> createTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        log.info("Received request to create ticket: {}", ticketRequestDto);
        TicketResponseDto createdTicket = ticketServiceImpl.createTicket(ticketRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @Operation(summary = "Get ticket by ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Ticket found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        log.info("Received request to get ticket by ID: {}", id);
        TicketResponseDto ticket = ticketServiceImpl.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @Operation(summary = "Get tickets by CPF.", responses = {
            @ApiResponse(responseCode = "200", description = "Tickets found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No tickets found for the given CPF", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDto>> getTicketByCpf(@PathVariable String cpf) {
        log.info("Received request to get tickets by CPF: {}", cpf);
        List<TicketResponseDto> tickets = ticketServiceImpl.getTicketByCpf(cpf);
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Cancel ticket by ID (soft-delete).", responses = {
            @ApiResponse(responseCode = "204", description = "Ticket canceled successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicketById(@PathVariable Long id) {
        log.info("Received request to cancel ticket by ID: {}", id);
        ticketServiceImpl.cancelTicketById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel tickets by CPF (soft-delete).", responses = {
            @ApiResponse(responseCode = "204", description = "Tickets canceled successfully"),
            @ApiResponse(responseCode = "404", description = "No tickets found for the given CPF", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/cancel-ticket-by-cpf/{cpf}")
    public ResponseEntity<Void> cancelTicketByCpf(@PathVariable String cpf) {
        log.info("Received request to cancel tickets by CPF: {}", cpf);
        ticketServiceImpl.cancelTicketByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check tickets by event ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Tickets found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No tickets found for the given event ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<List<TicketResponseDto>> checkTicketsByEvent(@PathVariable String eventId) {
        log.info("Received request to check tickets by event ID: {}", eventId);
        List<TicketResponseDto> tickets = ticketServiceImpl.checkTicketsByEvent(eventId);
        return ResponseEntity.ok(tickets);
    }
}