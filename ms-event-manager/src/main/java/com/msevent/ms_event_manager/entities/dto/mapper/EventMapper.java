package com.msevent.ms_event_manager.entities.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.msevent.ms_event_manager.entities.AddressCreate;
import com.msevent.ms_event_manager.entities.Event;
import com.msevent.ms_event_manager.entities.dto.EventRequestDto;
import com.msevent.ms_event_manager.services.ViaCepClient;

public class EventMapper {
    @Autowired
    private ViaCepClient viaCepClient;
    
    public static Event toEvent(EventRequestDto eventRequestDto) {
        return new ModelMapper().map(eventRequestDto, Event.class);
    }

    public Event toEventResponseDto(EventRequestDto eventRequestDto) {
        AddressCreate address = viaCepClient.getAddressByCep(eventRequestDto.getCep());

        Event eventResponseDto = new Event();
        eventResponseDto.setEventName(eventRequestDto.getEventName());
        eventResponseDto.setEventDateTime(eventRequestDto.getEventDateTime());
        eventResponseDto.setCep(eventRequestDto.getCep());
        eventResponseDto.setLogradouro(address.getLogradouro());
        eventResponseDto.setBairro(address.getBairro());
        eventResponseDto.setCidade(address.getLocalidade());
        eventResponseDto.setUf(address.getUf());

        return eventResponseDto;
    }
}
