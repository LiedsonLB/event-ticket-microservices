package com.msevent.ms_event_manager.services;

import java.util.List;

import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;


public interface EventService {
    public Event createEvent(EventRequestDto eventRequestDto);
    public List<Event> getEvents();
    public Event getEventById(String id);
    public Event updateEvent(String id, EventRequestDto eventRequestDto);
    public void deleteEvent(String id);
}
