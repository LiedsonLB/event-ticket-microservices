package com.msevent.ms_event_manager.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.repositories.EventRepository;
import com.msevent.ms_event_manager.services.EventService;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(String id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(String id, Event event) {
        Event eventToUpdate = eventRepository.findById(id).orElse(null);
        if (eventToUpdate == null) {
            return null;
        }
        eventToUpdate.setEventName(event.getEventName());
        eventToUpdate.setEventDateTime(event.getEventDateTime());
        eventToUpdate.setCep(event.getCep());
        eventToUpdate.setLogradouro(event.getLogradouro());
        eventToUpdate.setBairro(event.getBairro());
        eventToUpdate.setCidade(event.getCidade());
        eventToUpdate.setUf(event.getUf());
        return eventRepository.save(eventToUpdate);
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

}
