package com.msevent.ms_event_manager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.msevent.ms_event_manager.entities.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {}
