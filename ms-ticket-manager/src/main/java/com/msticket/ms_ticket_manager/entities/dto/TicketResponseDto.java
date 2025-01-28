package com.msticket.ms_ticket_manager.entities.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.msticket.ms_ticket_manager.entities.EventResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TicketResponseDto {
    private int ticketId;
    private String cpf;
    private String customerName;
    private String customerMail;
    private EventResponse event;

    private String BRLtotalAmount;
    private String USDtotalAmount;
    private String status;
    
    public TicketResponseDto(int ticketId, String cpf, String customerName, String customerMail, EventResponse event, String BRLtotalAmount, String USDtotalAmount, String status) {
        this.ticketId = ticketId;
        this.cpf = cpf;
        this.customerName = customerName;
        this.customerMail = customerMail;
        this.event = event;
        this.BRLtotalAmount = BRLtotalAmount;
        this.USDtotalAmount = USDtotalAmount;
        this.status = status;
    }
    
    public static String formatEventDateTime(LocalDateTime eventDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return eventDateTime.format(formatter);
    }
}