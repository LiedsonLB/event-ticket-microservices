package com.msticket.ms_ticket_manager.entities.dto.mapper;

import com.msticket.ms_ticket_manager.entities.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.services.client.EventClient;

@Component
public class TicketMapper {
    @Autowired
    private final EventClient eventClient;

    public TicketMapper(EventClient eventClient) {
        this.eventClient = eventClient;
    }

    public Ticket toTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        // logica para implementar o ticketId
        ticket.setCpf(ticketRequestDto.getCpf());
        ticket.setCustomerName(ticketRequestDto.getCustomerName());
        ticket.setCustomerMail(ticketRequestDto.getCustomerMail());

        EventResponse eventResponse = eventClient.getEventById(ticketRequestDto.getEventId());
        ticket.setEvent(eventResponse);

        ticket.setEventName(ticketRequestDto.getEventName());
        ticket.setBrlAmount(ticketRequestDto.getBrlAmount());
        ticket.setUsdAmount(ticketRequestDto.getUsdAmount());
        ticket.setStatus("concluído");
        return ticket;
    }
}
