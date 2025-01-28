package com.msevent.ms_event_manager.services;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msevent.ms_event_manager.entities.AddressResponse;
import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.exceptions.EntityNotFoundException;
import com.msevent.ms_event_manager.repositories.EventRepository;
import com.msevent.ms_event_manager.services.client.ViaCepClient;
import com.msevent.ms_event_manager.services.impl.EventServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ViaCepClient viaCepClient;

    private Event event;
    private EventRequestDto requestDto;
    private AddressResponse addressResponse;

    @BeforeEach
    public void setup() {
        event = new Event(
            "evento importante",
            LocalDateTime.of(2021, 10, 10, 10, 0),
            "64260000",
            "",
            "",
            "Piripiri",
            "PI"
        );

        requestDto = new EventRequestDto(
            "evento importante",
            LocalDateTime.of(2021, 10, 10, 10, 0),
            "64260000"
        );

        addressResponse = new AddressResponse(
            "",
            "",
            "Piripiri",
            "PI"
        );
    }

    @Test
    public void createEvent_WithValidData_ReturnsSavedEvent() {
        when(viaCepClient.getInfo("64260000")).thenReturn(addressResponse);

        System.out.println(addressResponse.getLogradouro());

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(requestDto);

        assertThat(createdEvent).isNotNull();
        assertThat(createdEvent.getEventName()).isEqualTo(requestDto.getEventName());
        assertThat(createdEvent.getEventDateTime()).isEqualTo(requestDto.getEventDateTime());
        assertThat(createdEvent.getCep()).isEqualTo(requestDto.getCep());
        assertThat(createdEvent.getLogradouro()).isEqualTo(addressResponse.getLogradouro());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    public void getEventById_WithExistingId_ReturnsEvent() {
        when(eventRepository.findById("123")).thenReturn(Optional.of(event));

        Event foundEvent = eventService.getEventById("123");

        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getEventName()).isEqualTo("evento importante");
        verify(eventRepository).findById("123");
    }

    @Test
    public void getEventById_WithNonExistingId_ThrowsException() {
        when(eventRepository.findById("123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEventById("123"))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Event with id: 123 not found in the database");

        verify(eventRepository).findById("123");
    }

    @Test
    public void getEvents_ReturnsListOfEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(event));

        List<Event> events = eventService.getEvents();

        assertThat(events).isNotEmpty();
        assertThat(events).hasSize(1);
        verify(eventRepository).findAll();
    }

    @Test
    public void updateEvent_WithValidData_ReturnsUpdatedEvent() {
        when(eventRepository.findById("123")).thenReturn(Optional.of(event));
        when(viaCepClient.getInfo("64260000")).thenReturn(addressResponse);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent("123", requestDto);

        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent.getEventName()).isEqualTo(requestDto.getEventName());
        assertThat(updatedEvent.getCep()).isEqualTo(requestDto.getCep());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    public void updateEvent_WithNonExistingId_ThrowsException() {
        when(eventRepository.findById("123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.updateEvent("123", requestDto))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Event with id: 123 not found in the database");

        verify(eventRepository).findById("123");
    }

    @Test
    public void deleteEvent_WithExistingId_DeletesEvent() {
        when(eventRepository.existsById("123")).thenReturn(true);

        eventService.deleteEvent("123");

        verify(eventRepository).deleteById("123");
    }

    @Test
    public void deleteEvent_WithNonExistingId_ThrowsException() {
        when(eventRepository.existsById("123")).thenReturn(false);

        assertThatThrownBy(() -> eventService.deleteEvent("123"))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Event with id: 123 not found in the database");

        verify(eventRepository, never()).deleteById("123");
    }
}
