package com.msticket.ms_ticket_manager.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EventResponse {
    private String id;
    private String eventName;
    private LocalDateTime eventDateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}
