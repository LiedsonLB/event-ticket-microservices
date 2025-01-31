package com.msticket.ms_ticket_manager.entities.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.msticket.ms_ticket_manager.entities.EventResponse;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventDto {
    private String eventId;
    private String eventName;
    private String eventDateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public static EventDto toEvent(EventResponse clientResponse) {
        EventDto eventDto = new EventDto();
        eventDto.setEventId(clientResponse.getId());
        eventDto.setEventName(clientResponse.getEventName());
        eventDto.setEventDateTime(formatDateTime(clientResponse.getDateTime()));
        eventDto.setLogradouro(clientResponse.getLogradouro());
        eventDto.setBairro(clientResponse.getBairro());
        eventDto.setCidade(clientResponse.getCidade());
        eventDto.setUf(clientResponse.getUf());
        return eventDto;
    }

    private static String formatDateTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}