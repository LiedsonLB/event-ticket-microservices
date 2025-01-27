package com.msticket.ms_ticket_manager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.mapper.TicketMapper;
import com.msticket.ms_ticket_manager.repositories.TicketRepository;
import com.msticket.ms_ticket_manager.services.TicketService;
import com.msticket.ms_ticket_manager.services.client.EventClient;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EventClient eventClient;

    @Override
    public Ticket createTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = ticketMapper.toTicket(ticketRequestDto);

        Ticket savedTicket = ticketRepository.save(ticket);

        return savedTicket;
    }

    @Override
    public Ticket getTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        return ticket;
    }

    @Override
    public Ticket updateTicket(String ticketId, TicketRequestDto ticketRequestDto) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        existingTicket.setCpf(ticketRequestDto.getCpf());
        existingTicket.setCustomerName(ticketRequestDto.getCustomerName());
        existingTicket.setCustomerMail(ticketRequestDto.getCustomerMail());
        existingTicket.setBrlAmount(ticketRequestDto.getBrlAmount());
        existingTicket.setUsdAmount(ticketRequestDto.getUsdAmount());

        EventResponse eventResponse = eventClient.getEventById(ticketRequestDto.getEventId());
        existingTicket.setEvent(eventResponse);

        existingTicket.setEventName(ticketRequestDto.getEventName());

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        return updatedTicket;
    }

    @Override
    public void deleteTicket(String ticketId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        ticketRepository.delete(existingTicket);
    }

    @Override
    public Ticket checkTicketByEvent(String eventId) {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}