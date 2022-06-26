package com.example.consumerpoliciafederal;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class ConsumerPoliciaFederalApplication {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsumerPoliciaFederalApplication.class, args);
    }

    @PostConstruct
    public void configurarCanais() {
        Queue fila1 = new Queue("policia.federal", true);
        amqpAdmin.declareQueue(fila1);
    }

}
