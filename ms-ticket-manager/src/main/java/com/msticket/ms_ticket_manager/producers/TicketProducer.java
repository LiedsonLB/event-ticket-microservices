package com.msticket.ms_ticket_manager.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketProducer {
    
    private final RabbitTemplate rabbitTemplate;

    public TicketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.name}")
    private String queue;

    public void sendMessage(String action, Long ticketId, String eventName) {
        String message = String.format("Ticket %s com sucesso para o evento %s. ID do Ticket: %s",
                action, eventName, ticketId);
        rabbitTemplate.convertAndSend(queue, message);
    }

    public void sendMessageForCpf(String cpf, int numberOfTickets) {
        String message = String.format("%d tickets cancelados com sucesso para o CPF: %s",
                numberOfTickets, cpf);
        rabbitTemplate.convertAndSend(queue, message);
    }
}