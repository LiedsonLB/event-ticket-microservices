package com.msevent.ms_event_manager.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msevent.ms_event_manager.entities.AddressResponse;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws/")
public interface ViaCepClient {

    @GetMapping("{cep}/json/")
    AddressResponse getInfo(@PathVariable String cep);
}