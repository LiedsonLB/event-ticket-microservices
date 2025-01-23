package com.msevent.ms_event_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.services.impl.EventServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventServiceImpl eventServiceImpl;
    
    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        Event createdEvent = eventServiceImpl.createEvent(eventRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getMethodName() {
        List<Event> events = eventServiceImpl.getEvents();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }
}
