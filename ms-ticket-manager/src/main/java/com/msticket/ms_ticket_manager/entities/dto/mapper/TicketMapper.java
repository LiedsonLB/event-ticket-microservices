package com.msticket.ms_ticket_manager.entities.dto.mapper;

import com.msticket.ms_ticket_manager.entities.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.exceptions.EventNotFoundException;
import com.msticket.ms_ticket_manager.services.client.EventClient;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TicketMapper {

    @Autowired
    private final EventClient eventClient;

    public TicketMapper(EventClient eventClient) {
        this.eventClient = eventClient;
    }

    public TicketResponseDto toTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setTicketId(ticket.getTicketId());
        ticketResponseDto.setCpf(ticket.getCpf());
        ticketResponseDto.setCustomerName(ticket.getCustomerName());
        ticketResponseDto.setCustomerMail(ticket.getCustomerMail());

        try {
            log.info("Evento encontrado: {}", ticket.getEventId());
            EventResponse eventResponse = eventClient.getEventById(ticket.getEventId());
            
            ticketResponseDto.setEvent(eventResponse);
        } catch (FeignException.NotFound ex) {
            log.error("Evento com ID {} não encontrado: {}", ticket.getEventId(), ex.getMessage());
            throw new EventNotFoundException("Evento não encontrado para o ID: " + ticket.getEventId());
        }

        ticketResponseDto.setBRLtotalAmount(ticket.getBRLamount());
        ticketResponseDto.setUSDtotalAmount(ticket.getUSDamount());
        ticketResponseDto.setStatus("concluído");

        return ticketResponseDto;
    }

    public Ticket toTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setCpf(ticketRequestDto.getCpf());
        ticket.setCustomerName(ticketRequestDto.getCustomerName());
        ticket.setCustomerMail(ticketRequestDto.getCustomerMail());
        
        // Preenchendo valores de BRL e USD
        ticket.setBRLamount(ticketRequestDto.getBRLamount());
        ticket.setUSDamount(ticketRequestDto.getUSDamount());
        
        // Definindo o ID e nome do evento
        ticket.setEventId(ticketRequestDto.getEventId());
        ticket.setEventName(ticketRequestDto.getEventName());

        return ticket;
    }
}