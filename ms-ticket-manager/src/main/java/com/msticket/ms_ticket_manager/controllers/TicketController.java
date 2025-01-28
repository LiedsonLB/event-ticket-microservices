package com.msticket.ms_ticket_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.services.impl.TicketServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "ms-ticket-management", description = "API for managing tickets.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private final TicketServiceImpl ticketServiceImpl;

    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto) {
        return ResponseEntity.ok(ticketServiceImpl.createTicket(ticketRequestDto));
    }

    @GetMapping("/get-tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketServiceImpl.getAllTickets());
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String ticketId) {
        return ResponseEntity.ok(ticketServiceImpl.getTicketById(ticketId));
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String ticketId, @RequestBody TicketRequestDto ticketRequestDto) {
        return ResponseEntity.ok(ticketServiceImpl.updateTicket(ticketId, ticketRequestDto));
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String ticketId) {
        ticketServiceImpl.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<Ticket> checkTicketByEvent(@PathVariable String eventId) {
        return ResponseEntity.noContent().build();
    }
    
}
