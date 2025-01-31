package com.msevent.ms_event_manager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msevent.ms_event_manager.entities.AddressResponse;
import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.exceptions.EntityNotFoundException;
import com.msevent.ms_event_manager.repositories.EventRepository;
import com.msevent.ms_event_manager.services.EventService;
import com.msevent.ms_event_manager.services.client.ViaCepClient;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ViaCepClient viaCepClient;

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(String id) {
        return eventRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Event with id: %s not found in the database", id))
        );
    }

    @Override
    public Event createEvent(EventRequestDto eventRequestDto) {
        
        AddressResponse address = viaCepClient.getInfo(eventRequestDto.getCep());

        Event event = new Event();
        event.setEventName(eventRequestDto.getEventName());
        event.setDateTime(eventRequestDto.getDateTime());
        event.setCep(eventRequestDto.getCep());
        
        event.setLogradouro(address.getLogradouro());
        event.setBairro(address.getBairro());
        event.setCidade(address.getLocalidade());
        event.setUf(address.getUf());

        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(String id, EventRequestDto eventRequestDto) {
        Event eventToUpdate = eventRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Event with id: %s not found in the database", id))
        );

        eventToUpdate.setEventName(eventRequestDto.getEventName());
        eventToUpdate.setDateTime(eventRequestDto.getDateTime());
        eventToUpdate.setCep(eventRequestDto.getCep());

        AddressResponse address = viaCepClient.getInfo(eventRequestDto.getCep());
        eventToUpdate.setLogradouro(address.getLogradouro());
        eventToUpdate.setBairro(address.getBairro());
        eventToUpdate.setCidade(address.getLocalidade());
        eventToUpdate.setUf(address.getUf());

        return eventRepository.save(eventToUpdate);
    }

    @Override
    public void deleteEvent(String id) {
        if(!eventRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Event with id: %s not found in the database", id));
        }
        eventRepository.deleteById(id);
    }
}