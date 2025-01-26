package com.msticket.ms_ticket_manager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msticket.ms_ticket_manager.entities.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {}
