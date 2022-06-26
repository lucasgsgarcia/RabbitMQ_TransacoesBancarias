package com.example.transacoesproducer;


import com.google.javascript.jscomp.jarjar.com.google.gson.Gson;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.example.transacoesproducer")
public class TransacoesProducerApplication {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Queue filaTransacoes;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TransacoesProducerApplication.class, args);
    }

    @PostConstruct
    public void criarFila() {
        filaTransacoes = new Queue("transacoes.financeiras", true);
        amqpAdmin.declareQueue(filaTransacoes);
        processarArquivotransacoes();
    }


    public void processarArquivotransacoes() {
        List<Transacao> transacoes = new LeitorArquivo().lerArquivo();
        Gson gson = new Gson();
        for (Transacao transacao : transacoes) {
            System.out.println(gson.toJson(transacao));
            rabbitTemplate.convertAndSend(this.filaTransacoes.getName(), gson.toJson(transacao));
        }
    }

}
