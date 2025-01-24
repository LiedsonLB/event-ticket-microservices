package com.msevent.ms_event_manager.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Document(collection = "events")
public class Event {
    @Id
    private String id;
    @Field("eventName")
    private String eventName;
    @Field("dateTime")
    private LocalDateTime eventDateTime;
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

}
