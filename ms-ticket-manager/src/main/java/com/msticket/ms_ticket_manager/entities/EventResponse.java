package com.msticket.ms_ticket_manager.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.msticket.ms_ticket_manager.entities.dto.TicketResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EventResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("eventName")
    private String eventName;

    @JsonProperty("dateTime")
    private String dateTime;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("cidade")
    private String cidade;

    @JsonProperty("uf")
    private String uf;
    
    public EventResponse(String eventId, String eventName, LocalDateTime eventDateTime, String logradouro, String bairro, String cidade, String uf) {
        this.id = eventId;
        this.eventName = eventName;
        this.dateTime = TicketResponseDto.formatEventDateTime(eventDateTime);
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }
}