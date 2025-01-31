package com.msevent.ms_event_manager.entities.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.msevent.ms_event_manager.entities.AddressResponse;
import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.services.client.ViaCepClient;

public class EventMapper {
    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    public EventMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    public static Event toEvent(EventRequestDto eventRequestDto) {
        return new ModelMapper().map(eventRequestDto, Event.class);
    }

    public Event toEventResponseDto(EventRequestDto eventRequestDto) {
        AddressResponse address = viaCepClient.getInfo(eventRequestDto.getCep());

        Event eventResponseDto = new Event();
        eventResponseDto.setEventName(eventRequestDto.getEventName());
        eventResponseDto.setDateTime(eventRequestDto.getDateTime());
        eventResponseDto.setCep(eventRequestDto.getCep());
        eventResponseDto.setLogradouro(address.getLogradouro());
        eventResponseDto.setBairro(address.getBairro());
        eventResponseDto.setCidade(address.getLocalidade());
        eventResponseDto.setUf(address.getUf());

        return eventResponseDto;
    }
}
