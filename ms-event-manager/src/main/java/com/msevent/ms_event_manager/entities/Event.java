package com.msevent.ms_event_manager.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }
    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
}
