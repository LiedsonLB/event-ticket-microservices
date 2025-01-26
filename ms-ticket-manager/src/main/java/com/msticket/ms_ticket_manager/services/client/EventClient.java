package com.msticket.ms_ticket_manager.services.client;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eventservice", url = "http://localhost:8080/api/events/get-event")
public interface EventClient {

    @GetMapping("/{id}")
    EventResponse getEventById(@PathVariable("id") String id);
}
