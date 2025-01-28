package com.msticket.ms_ticket_manager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.entities.dto.mapper.TicketMapper;
import com.msticket.ms_ticket_manager.repositories.TicketRepository;
import com.msticket.ms_ticket_manager.services.TicketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        
        Ticket savedTicket = ticketMapper.toTicket(ticketRequestDto);

        savedTicket.setStatus("concluído");

        ticketRepository.save(savedTicket);

        TicketResponseDto ticketResponseDto = ticketMapper.toTicketResponseDto(savedTicket);

        return ticketResponseDto;
    }

    @Override
    public TicketResponseDto getTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        TicketResponseDto ticketResponseDto = ticketMapper.toTicketResponseDto(ticket);
        return ticketResponseDto;
    }

    @Override
    public TicketResponseDto updateTicket(String ticketId, TicketRequestDto ticketRequestDto) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        existingTicket.setCpf(ticketRequestDto.getCpf());
        existingTicket.setCustomerName(ticketRequestDto.getCustomerName());
        existingTicket.setCustomerMail(ticketRequestDto.getCustomerMail());
        existingTicket.setBRLamount(ticketRequestDto.getBRLamount());
        existingTicket.setUSDamount(ticketRequestDto.getUSDamount());

        existingTicket.setEventId(ticketRequestDto.getEventId());

        existingTicket.setEventName(ticketRequestDto.getEventName());

        ticketRepository.save(existingTicket);

        TicketResponseDto ticketResponseDto = ticketMapper.toTicketResponseDto(existingTicket);

        return ticketResponseDto;
    }

    @Override
    public void deleteTicket(String ticketId) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        existingTicket.setStatus("cancelado");
        ticketRepository.save(existingTicket);
    }

    @Override
    public TicketResponseDto checkTicketByEvent(String eventId) {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}