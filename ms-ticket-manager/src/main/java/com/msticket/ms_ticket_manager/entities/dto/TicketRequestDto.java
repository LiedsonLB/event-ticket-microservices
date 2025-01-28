package com.msticket.ms_ticket_manager.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TicketRequestDto {
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    @JsonProperty("BRLamount")
    private String BRLamount;
    @JsonProperty("USDamount")
    private String USDamount;
}