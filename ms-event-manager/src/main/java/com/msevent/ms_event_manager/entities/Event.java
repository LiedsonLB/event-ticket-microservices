package com.msevent.ms_event_manager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Document(collection = "events")
public class Event {
    @Id
    private String id;
    @Field("eventName")
    private String eventName;
    @Field("dateTime")
    private String eventDateTime;
    @Field("cep")
    private String cep;
    @Field("logradouro")
    private String logradouro;
    @Field("bairro")
    private String bairro;
    @Field("cidade")
    private String cidade;
    @Field("uf")
    private String uf;

    public Event(String eventName, String eventDateTime, String cep, String logradouro, String bairro, String cidade, String uf) {
        this.eventName = eventName;
        this.eventDateTime = eventDateTime;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }
}
