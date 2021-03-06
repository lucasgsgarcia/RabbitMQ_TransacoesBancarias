package com.example.consumerreceitafederal;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ConsumerReceitaFederalApplication {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsumerReceitaFederalApplication.class, args);
    }

    @PostConstruct
    public void configurarCanais() {
        Queue fila2 = new Queue("receita.federal", true);
        amqpAdmin.declareQueue(fila2);
    }
}
