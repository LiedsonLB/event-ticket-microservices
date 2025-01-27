package com.msticket.ms_ticket_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;

@Service
public interface TicketService {
    public Ticket createTicket(TicketRequestDto ticketRequestDto);
    public Ticket getTicketById(String ticketId);
    public Ticket updateTicket(String ticketId, TicketRequestDto ticketRequestDto);
    public void deleteTicket(String ticketId);
    public Ticket checkTicketByEvent(String eventId);
    public List<Ticket> getAllTickets();
}