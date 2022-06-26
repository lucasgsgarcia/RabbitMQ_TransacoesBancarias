package com.example.transacoesconsumerprincipal;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TransacoesConsumerPrincipalApplication {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Queue filaTransacoes;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TransacoesConsumerPrincipalApplication.class, args);
    }

    @PostConstruct
    public void criarFila() {
        filaTransacoes = new Queue("transacoes.financeiras", true);
        amqpAdmin.declareQueue(filaTransacoes);
    }

}
