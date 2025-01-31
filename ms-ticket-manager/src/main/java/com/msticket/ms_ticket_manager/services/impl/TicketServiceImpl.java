package com.msticket.ms_ticket_manager.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.entities.dto.mapper.TicketMapper;
import com.msticket.ms_ticket_manager.exceptions.EventNotFoundException;
import com.msticket.ms_ticket_manager.exceptions.InvalidQueryParameterException;
import com.msticket.ms_ticket_manager.exceptions.TicketNotFoundException;
import com.msticket.ms_ticket_manager.producers.TicketProducer;
import com.msticket.ms_ticket_manager.repositories.TicketRepository;
import com.msticket.ms_ticket_manager.services.TicketService;
import com.msticket.ms_ticket_manager.services.client.EventClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private TicketProducer ticketProducer;

    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        if (ticketRequestDto.getEventId() == null) {
            throw new InvalidQueryParameterException("ID do evento não pode ser nulo.");
        }

        if (eventClient.getEventById(ticketRequestDto.getEventId()) == null) {
            throw new EventNotFoundException("Evento não encontrado com o ID: " + ticketRequestDto.getEventId());
            
        }

        Ticket ticket = ticketMapper.toTicket(ticketRequestDto);

        Ticket savedTicket = ticketRepository.save(ticket);

        log.info("Ticket criado com sucesso: {}", savedTicket.getId());
        ticketProducer.sendMessage("criado", savedTicket.getTicketId(), ticketRequestDto.getEventName());
        return ticketMapper.toTicketResponseDto(savedTicket);
    }

    @Override
    public TicketResponseDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);

        if (ticket == null) {
            throw new TicketNotFoundException("Ticket não encontrado com o ID: " + ticketId);
        }

        return ticketMapper.toTicketResponseDto(ticket);
    }

    @Override
    public List<TicketResponseDto> getTicketByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("Nenhum ticket encontrado para o CPF: " + cpf);
        }
        return tickets.stream()
                .map(ticketMapper::toTicketResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto updateTicket(Long ticketId, TicketRequestDto ticketRequestDto) {
        Ticket existingTicket = ticketRepository.findByTicketId(ticketId);

        if (existingTicket == null) {
            throw new TicketNotFoundException("Ticket não encontrado com o ID: " + ticketId);
        }

        existingTicket.setCpf(ticketRequestDto.getCpf());
        existingTicket.setCustomerName(ticketRequestDto.getCustomerName());
        existingTicket.setCustomerMail(ticketRequestDto.getCustomerMail());
        existingTicket.setBRLamount(ticketRequestDto.getBRLamount());
        existingTicket.setUSDamount(ticketRequestDto.getUSDamount());
        existingTicket.setEventId(ticketRequestDto.getEventId());
        existingTicket.setEventName(ticketRequestDto.getEventName());

        Ticket updatedTicket = ticketRepository.save(existingTicket);

        log.info("Ticket atualizado com sucesso: {}", updatedTicket.getId());
        ticketProducer.sendMessage("atualizado", updatedTicket.getTicketId(), ticketRequestDto.getEventName());
        return ticketMapper.toTicketResponseDto(updatedTicket);
    }

    @Override
    public void cancelTicketById(Long ticketId) {
        Ticket existingTicket = ticketRepository.findByTicketId(ticketId);

        if (existingTicket == null) {
            throw new TicketNotFoundException("Ticket não encontrado com o ID: " + ticketId);
        }

        existingTicket.setStatus("cancelado");
        ticketRepository.save(existingTicket);
        ticketProducer.sendMessage("cancelado", existingTicket.getTicketId(), existingTicket.getEventName());
        log.info("Ticket cancelado com sucesso: {}", ticketId);
    }

    @Override
    public void cancelTicketByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("Nenhum ticket encontrado para o CPF: " + cpf);
        }

        tickets.forEach(ticket -> ticket.setStatus("cancelado"));
        ticketRepository.saveAll(tickets);
        ticketProducer.sendMessageForCpf(cpf, tickets.size());
        log.info("Todos os tickets do CPF {} foram cancelados.", cpf);
    }

    @Override
    public List<TicketResponseDto> checkTicketsByEvent(String eventId) {
        if(eventId == null) {
            throw new InvalidQueryParameterException("ID do evento não pode ser nulo.");
        }
        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("Nenhum ticket encontrado para o evento com ID: " + eventId);
        }
        return tickets.stream()
                .map(ticketMapper::toTicketResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}