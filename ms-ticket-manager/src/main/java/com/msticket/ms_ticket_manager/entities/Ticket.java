package com.msticket.ms_ticket_manager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private Long ticketId;
    @NotBlank(message = "cpf cannot be empty")
    private String cpf;
    @NotBlank(message = "customerName cannot be empty")
    @Size(max = 100, message = "name cannot exceed 100 characters.")
    private String customerName;
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Invalid CEP format")
    @Email(message = "Invalid email format")
    private String customerMail;
    private String eventId;
    @Size(max = 100, message = "eventName cannot exceed 150 characters")
    private String eventName;
    @Pattern(regexp = "R\\$\\s?\\d+(,\\d{2})?", message = "Invalid BRL amount format")
    private String BRLamount;
    @Pattern(regexp = "R\\$\\s?\\d+(,\\d{2})?", message = "Invalid BRL amount format")
    private String USDamount;
    @Pattern(regexp = "^(concluído|cancelado)$", message = "Invalid status. Must be 'concluído' or 'pendente'")
    private String status;

    public Ticket(String cpf, String customerName, String customerMail, String eventId, String eventName, String BRLamount, String USDamount, String status) {
        this.cpf = cpf;
        this.customerName = customerName;
        this.customerMail = customerMail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
        this.status = status;
    }
}
