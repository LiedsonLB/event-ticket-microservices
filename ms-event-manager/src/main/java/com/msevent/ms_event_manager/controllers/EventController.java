package com.msevent.ms_event_manager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.exceptions.ErrorMessage;
import com.msevent.ms_event_manager.services.impl.EventServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "ms-event-management", description = "API for managing events.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private final EventServiceImpl eventServiceImpl;

    @Operation(summary = "Create a new event.", responses = {
            @ApiResponse(responseCode = "201", description = "Event created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "422", description = "Invalid data provided", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - Event already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        Event createdEvent = eventServiceImpl.createEvent(eventRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @Operation(summary = "Get all events.", responses = {
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "No events found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

    })
    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getMethodName() {
        List<Event> events = eventServiceImpl.getEvents();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @Operation(summary = "Get event by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

    })
    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable(value = "id") String id) {
        Event event = eventServiceImpl.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @Operation(summary = "Get all events sorted.", responses = {
            @ApiResponse(responseCode = "200", description = "List of all events sorted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "No events found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> events = eventServiceImpl.getEvents();
        events.sort((e1, e2) -> e1.getEventName().compareToIgnoreCase(e2.getEventName()));
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @Operation(summary = "Update event by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - Tickets linked to this event", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

    })
    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable(value = "id") String id,
            @RequestBody EventRequestDto eventRequestDto) {
        Event updatedEvent = eventServiceImpl.updateEvent(id, eventRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEvent);
    }

    @Operation(summary = "Delete event by id.", responses = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - Tickets linked to this event", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

    })
    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(value = "id") String id) {
        eventServiceImpl.deleteEvent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
