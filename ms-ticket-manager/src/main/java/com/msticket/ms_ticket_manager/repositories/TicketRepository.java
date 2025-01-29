package com.msticket.ms_ticket_manager.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msticket.ms_ticket_manager.entities.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    public List<Ticket> findByCpf(String cpf);
    public List<Ticket> findByEventId(String eventId);
    public Ticket findByTicketId(Long ticketId);
}
