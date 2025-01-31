package com.msticket.ms_ticket_manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;

@Service
public interface TicketService {
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);
    public TicketResponseDto getTicketById(Long ticketId);
    public List<TicketResponseDto> getTicketByCpf(String cpf);
    public TicketResponseDto updateTicket(Long ticketId, TicketRequestDto ticketRequestDto);
    public void cancelTicketById(Long ticketId);
    public void cancelTicketByCpf(String cpf);
    public List<TicketResponseDto> checkTicketsByEvent(String eventId);
    public List<Ticket> getAllTickets();
}