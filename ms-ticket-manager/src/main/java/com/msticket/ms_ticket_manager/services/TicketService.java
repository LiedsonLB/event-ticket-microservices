package com.msticket.ms_ticket_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;

@Service
public interface TicketService {
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);
    public TicketResponseDto getTicketById(String ticketId);
    public TicketResponseDto updateTicket(String ticketId, TicketRequestDto ticketRequestDto);
    public void deleteTicket(String ticketId);
    public TicketResponseDto checkTicketByEvent(String eventId);
    public List<Ticket> getAllTickets();
}