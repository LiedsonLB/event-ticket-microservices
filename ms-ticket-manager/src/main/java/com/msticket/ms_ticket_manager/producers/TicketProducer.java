package com.msticket.ms_ticket_manager.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        
        try {
            rabbitTemplate.convertAndSend(queue, message);
            log.info("Mensagem enviada com sucesso para a fila: {}. Mensagem: {}", queue, message);
        } catch (Exception e) {
            log.error("Erro ao enviar a mensagem para a fila: {}. Mensagem: {}", queue, message, e);
        }
    }

    public void sendMessageForCpf(String cpf, int numberOfTickets) {
        String message = String.format("%d tickets cancelados com sucesso para o CPF: %s",
                numberOfTickets, cpf);
        
        try {
            rabbitTemplate.convertAndSend(queue, message);
            log.info("Mensagem enviada com sucesso para a fila: {}. Mensagem: {}", queue, message);
        } catch (Exception e) {
            log.error("Erro ao enviar a mensagem para a fila: {}. Mensagem: {}", queue, message, e);
        }
    }
}