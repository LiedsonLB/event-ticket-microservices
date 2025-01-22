package com.msevent.ms_event_manager.services;

import java.util.List;

import com.msevent.ms_event_manager.entities.Event;

public interface EventService {
    public List<Event> getEvents();
    public Event getEventById(String id);
    public Event createEvent(Event event);
    public Event updateEvent(String id, Event event);
    public void deleteEvent(String id);
}
