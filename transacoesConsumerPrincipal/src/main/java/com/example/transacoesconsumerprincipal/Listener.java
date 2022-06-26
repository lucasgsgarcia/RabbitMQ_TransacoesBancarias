package com.example.transacoesconsumerprincipal;

import com.google.javascript.jscomp.jarjar.com.google.gson.Gson;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.*;

@Component
public class Listener {
    @RabbitListener(queues = "transacoes.financeiras")
    public void listen(String in) {
        processarTransacao(in);
    }

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private FanoutExchange fanout = new FanoutExchange("exemplo.exchange.fanout", true, false);

    @PostConstruct
    public void configurarCanais() {
        Queue fila1 = new Queue("policia.federal", true);
        amqpAdmin.declareQueue(fila1);
        Queue fila2 = new Queue("receita.federal", true);
        amqpAdmin.declareQueue(fila2);

        FanoutExchange fanout = new FanoutExchange("exemplo.exchange.fanout", true, false);
        amqpAdmin.declareExchange(fanout);
        Binding binding = BindingBuilder.bind(fila1).to(fanout);
        amqpAdmin.declareBinding(binding);
        binding = BindingBuilder.bind(fila2).to(fanout);
        amqpAdmin.declareBinding(binding);
    }

    public void processarTransacao(String in) {
        try
        {
            Gson gson = new Gson();
            Transacao t = gson.fromJson(in, Transacao.class);
            System.out.println(gson.toJson(t));
            if(t.getValor() >= 40000) {
                rabbitTemplate.convertAndSend(fanout.getName(), "", "Processando transação suspeita: " + gson.toJson(t));
            }
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }
}
