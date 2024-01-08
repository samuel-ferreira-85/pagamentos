package com.samuel.pedidos.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentosListener {

    @RabbitListener(queues = "pagamento-concluido")
    public void listenerMessage(Message message) {
        System.out.println("Recebi a mensagem : " + message.toString());
    }
}
