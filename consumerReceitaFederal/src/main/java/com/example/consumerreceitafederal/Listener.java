package com.example.consumerreceitafederal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @RabbitListener(queues = "receita.federal")
    public void listen(String in) {
        System.out.println("Receita Federal\n"+in);
    }

}
