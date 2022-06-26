package com.example.consumerpoliciafederal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    @RabbitListener(queues = "policia.federal")
    public void listen(String in) {
        System.out.println("Polícia federal\n"+in);
    }
}
