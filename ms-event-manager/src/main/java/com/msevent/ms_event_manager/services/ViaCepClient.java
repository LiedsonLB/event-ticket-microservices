package com.msevent.ms_event_manager.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msevent.ms_event_manager.entities.AddressCreate;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws/")
public interface ViaCepClient {
    @GetMapping("/{cep}/json")
    AddressCreate getAddressByCep(@PathVariable("cep") String cep);
}
