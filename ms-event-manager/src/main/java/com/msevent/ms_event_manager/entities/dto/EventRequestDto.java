package com.msevent.ms_event_manager.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventRequestDto {
    private String id;
    @NotBlank(message = "name cannot be empty")
    @Size(max = 100, message = "Event name cannot exceed 100 characters.")
    private String eventName;
    @JsonProperty("eventDateTime") @NotNull(message = "Event date and time cannot be empty")
    private String eventDateTime;
    @NotBlank(message = "cep cannot be empty")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Invalid CEP format")
    private String cep;
    
    public EventRequestDto(String eventName, String eventDateTime, String cep) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.cep = cep;
    }
}
