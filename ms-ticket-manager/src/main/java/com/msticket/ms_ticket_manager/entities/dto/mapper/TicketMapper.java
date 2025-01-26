package com.msticket.ms_ticket_manager.entities.dto.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TicketMapper {
    @Autowired
    public TicketMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
    }
}
