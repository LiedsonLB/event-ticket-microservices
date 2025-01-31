package com.msticket.ms_ticket_manager.entities.dto.mapper;

import com.msticket.ms_ticket_manager.entities.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import com.msticket.ms_ticket_manager.entities.dto.EventDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.exceptions.EventNotFoundException;
import com.msticket.ms_ticket_manager.repositories.TicketRepository;
import com.msticket.ms_ticket_manager.services.client.EventClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketMapper {

    @Autowired
    private final EventClient eventClient;

    @Autowired
    private final TicketRepository ticketRepository;

    public TicketResponseDto toTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setTicketId(ticket.getTicketId());
        ticketResponseDto.setCpf(ticket.getCpf());
        ticketResponseDto.setCustomerName(ticket.getCustomerName());
        ticketResponseDto.setCustomerMail(ticket.getCustomerMail());

        try {
            EventResponse eventResponse = eventClient.getEventById(ticket.getEventId());
            EventDto eventDto = EventDto.toEvent(eventResponse);
            log.info("event found {}", ticket.getEventId());
            
            ticketResponseDto.setEvent(eventDto);
        } catch (FeignException.NotFound ex) {
            log.error("event ID {} not found: {}", ticket.getEventId(), ex.getMessage());
            throw new EventNotFoundException("event ID not found: " + ticket.getEventId() + " - " + ex.getMessage());
        }

        ticketResponseDto.setBRLtotalAmount(ticket.getBRLamount());
        ticketResponseDto.setUSDtotalAmount(ticket.getUSDamount());
        ticketResponseDto.setStatus(ticket.getStatus());

        return ticketResponseDto;
    }

    public Ticket toTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketRepository.count() + 1);
        ticket.setCpf(ticketRequestDto.getCpf());
        ticket.setCustomerName(ticketRequestDto.getCustomerName());
        ticket.setCustomerMail(ticketRequestDto.getCustomerMail());

        ticket.setBRLamount(ticketRequestDto.getBRLamount());
        ticket.setUSDamount(ticketRequestDto.getUSDamount());

        ticket.setEventId(ticketRequestDto.getEventId());
        ticket.setEventName(ticketRequestDto.getEventName());

        ticket.setStatus("concluído");

        return ticket;
    }
}