package com.msticket.ms_ticket_manager.services.client;

import com.msticket.ms_ticket_manager.entities.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eventservice", url = "${eventservice.url}")
public interface EventClient {

    @GetMapping("/{id}")
    EventResponse getEventById(@PathVariable("id") String id);
}
