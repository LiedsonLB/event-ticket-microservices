package com.msevent.ms_event_manager.entities.dto;

import java.time.LocalDateTime;

public class EventRequestDto {
    private String eventName;
    private LocalDateTime eventDateTime;
    private String cep;

    public EventRequestDto(String eventName, LocalDateTime eventDateTime, String cep) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.cep = cep;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }
    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    
}
