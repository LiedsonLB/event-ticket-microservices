package com.msticket.ms_ticket_manager.services;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import com.msticket.ms_ticket_manager.entities.Ticket;
import com.msticket.ms_ticket_manager.entities.dto.EventDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketRequestDto;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;
import com.msticket.ms_ticket_manager.entities.dto.mapper.TicketMapper;
import com.msticket.ms_ticket_manager.exceptions.InvalidQueryParameterException;
import com.msticket.ms_ticket_manager.exceptions.TicketNotFoundException;
import com.msticket.ms_ticket_manager.producers.TicketProducer;
import com.msticket.ms_ticket_manager.repositories.TicketRepository;
import com.msticket.ms_ticket_manager.services.client.EventClient;
import com.msticket.ms_ticket_manager.services.impl.TicketServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private EventClient eventClient;

    @Mock
    private TicketProducer ticketProducer;

    private Ticket ticket;
    private TicketRequestDto requestDto;
    private TicketResponseDto responseDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket(
            "12345678901", 
            "Liedson Barros", 
            "liedson@outlook.com", 
            "1", 
            "Evento de Java", 
            "R$ 24,00", 
            "$ 20.0", 
            "ativo"
        );
        ticket.setTicketId(1L);

        requestDto = new TicketRequestDto(
            "Liedson Barros", 
            "12345678901", 
            "liedson@outlook.com", 
            "1", 
            "Evento de Java", 
            "R$ 24,00", 
            "$ 20.0"
        );

        responseDto = new TicketResponseDto(
            1L, 
            "12345678901", 
            "Liedson Barros", 
            "liedson@outlook.com", 
            EventDto.toEvent(new EventResponse(
                "1", 
                "Evento de Java", 
                "2021-12-30T23:59:59", 
                "Rua ola mundo", 
                "Bairro Spring", 
                "Bela Java", 
                "JV"
            )),
            "R$ 24,00", 
            "$ 20.0", 
            "concluído"
        );
    }

    @Test
    public void createTicket_WithValidData_ReturnsSavedTicket() {
        when(eventClient.getEventById("1")).thenReturn(new EventResponse());
        when(ticketMapper.toTicket(requestDto)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        TicketResponseDto createdTicket = ticketService.createTicket(requestDto);

        assertThat(createdTicket).isNotNull();
        assertThat(createdTicket.getCpf()).isEqualTo(requestDto.getCpf());
        verify(ticketRepository).save(ticket);
        verify(ticketProducer).sendMessage("criado", ticket.getTicketId(), ticket.getEventName());
    }

    @Test
    public void getTicketById_WithExistingId_ReturnsTicket() {
        when(ticketRepository.findByTicketId(1L)).thenReturn(ticket);
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        TicketResponseDto foundTicket = ticketService.getTicketById(1L);

        assertThat(foundTicket).isNotNull();
        assertThat(foundTicket.getTicketId()).isEqualTo(1L);
        verify(ticketRepository).findByTicketId(1L);
    }

    @Test
    public void getTicketById_WithNonExistingId_ThrowsException() {
        when(ticketRepository.findByTicketId(1L)).thenReturn(null);

        assertThatThrownBy(() -> ticketService.getTicketById(1L))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage("Ticket não encontrado com o ID: 1");
    }

    @Test
    public void updateTicket_WithValidData_ReturnsUpdatedTicket() {
        when(ticketRepository.findByTicketId(1L)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        TicketResponseDto updatedTicket = ticketService.updateTicket(1L, requestDto);

        assertThat(updatedTicket).isNotNull();
        assertThat(updatedTicket.getCustomerName()).isEqualTo(requestDto.getCustomerName());
        verify(ticketRepository).save(ticket);
        verify(ticketProducer).sendMessage("atualizado", ticket.getTicketId(), ticket.getEventName());
    }

    @Test
    public void cancelTicketById_WithExistingId_CancelsTicket() {
        when(ticketRepository.findByTicketId(1L)).thenReturn(ticket);

        ticketService.cancelTicketById(1L);

        verify(ticketRepository).save(ticket);
        verify(ticketProducer).sendMessage("cancelado", ticket.getTicketId(), ticket.getEventName());
    }

    @Test
    public void cancelTicketByCpf_WithExistingCpf_CancelsTickets() {
        when(ticketRepository.findByCpf("12345678901")).thenReturn(List.of(ticket));

        ticketService.cancelTicketByCpf("12345678901");

        verify(ticketRepository).saveAll(anyList());
        verify(ticketProducer).sendMessageForCpf("12345678901", 1);
    }

    @Test
    public void getTicketByCpf_WithExistingCpf_ReturnsTickets() {
        when(ticketRepository.findByCpf("12345678901")).thenReturn(List.of(ticket));
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        List<TicketResponseDto> foundTickets = ticketService.getTicketByCpf("12345678901");

        assertThat(foundTickets).isNotEmpty();
        assertThat(foundTickets).hasSize(1);
        assertThat(foundTickets.get(0).getCpf()).isEqualTo("12345678901");
        verify(ticketRepository).findByCpf("12345678901");
    }

    @Test
    public void getTicketByCpf_WithNonExistingCpf_ThrowsException() {
        when(ticketRepository.findByCpf("12345678901")).thenReturn(List.of());

        assertThatThrownBy(() -> ticketService.getTicketByCpf("12345678901"))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage("Nenhum ticket encontrado para o CPF: 12345678901");

        verify(ticketRepository).findByCpf("12345678901");
    }

    @Test
    public void getTicketByEventId_WithExistingEventId_ReturnsTickets() {
        when(ticketRepository.findByEventId("1")).thenReturn(List.of(ticket));
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        List<TicketResponseDto> foundTickets = ticketService.checkTicketsByEvent("1");

        assertThat(foundTickets).isNotEmpty();
        assertThat(foundTickets).hasSize(1);
        assertThat(foundTickets.get(0).getEvent().getEventId()).isEqualTo("1");
        verify(ticketRepository).findByEventId("1");
    }

    @Test
    public void getTicketByEventId_WithNonExistingEventId_ThrowsException() {
        when(ticketRepository.findByEventId("1")).thenReturn(List.of());

        assertThatThrownBy(() -> ticketService.checkTicketsByEvent("1"))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage("Nenhum ticket encontrado para o evento com ID: 1");

        verify(ticketRepository).findByEventId("1");
    }

    @Test
    public void getTicketByEventId_WithNullEventId_ThrowsException() {
        assertThatThrownBy(() -> ticketService.checkTicketsByEvent(null))
                .isInstanceOf(InvalidQueryParameterException.class)
                .hasMessage("ID do evento não pode ser nulo.");
    }

    @Test
    public void checkTicketsByEvent_WithExistingEventId_ReturnsTickets() {
        when(ticketRepository.findByEventId("1")).thenReturn(List.of(ticket));
        when(ticketMapper.toTicketResponseDto(ticket)).thenReturn(responseDto);

        List<TicketResponseDto> foundTickets = ticketService.checkTicketsByEvent("1");

        assertThat(foundTickets).isNotEmpty();
        assertThat(foundTickets).hasSize(1);
        assertThat(foundTickets.get(0).getEvent().getEventId()).isEqualTo("1");
        verify(ticketRepository).findByEventId("1");
    }
}
