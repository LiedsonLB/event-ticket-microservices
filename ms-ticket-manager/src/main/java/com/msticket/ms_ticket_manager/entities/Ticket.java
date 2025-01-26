package com.msticket.ms_ticket_manager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    @NotBlank(message = "cpf cannot be empty")
    @Size(max = 50, message = "ticketId cannot exceed 50 characters")
    private String ticketId;
    @NotBlank(message = "cpf cannot be empty")
    private String cpf;
    @NotBlank(message = "customerName cannot be empty")
    @Size(max = 100, message = "name cannot exceed 100 characters.")
    private String customerName;
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Invalid CEP format")
    @Email(message = "Invalid email format")
    private String customerMail;
    @NotBlank(message = "eventId cannot be empty")
    private String eventId;
    @Size(max = 100, message = "eventName cannot exceed 150 characters")
    private String eventName;
    @Pattern(regexp = "R\\$\\s?\\d+(,\\d{2})?", message = "Invalid BRL amount format")
    private String brlAmount;
    @Pattern(regexp = "R\\$\\s?\\d+(,\\d{2})?", message = "Invalid BRL amount format")
    private String usdAmount;
    @Pattern(regexp = "^(concluído|cancelado)$", message = "Invalid status. Must be 'concluído' or 'pendente'")
    private String status;

}
