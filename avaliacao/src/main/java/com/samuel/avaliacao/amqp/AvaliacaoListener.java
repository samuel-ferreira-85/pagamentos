package com.samuel.avaliacao.amqp;

import com.samuel.avaliacao.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoListener {

    @RabbitListener(queues = "pagamentos.detalhes-avaliacao")
    public void listenerMessage(@Payload PagamentoDto pagamento) {
        System.out.println(pagamento.getId());
        System.out.println(pagamento.getNumero());

        if (pagamento.getNumero().equals("0000")) {
            throw new RuntimeException("não consegui processar");
        }

        String mensagem = """
                Necessário criar registro de avaliação para o pedido: %s 
                Id do pagamento: %s
                Nome do cliente: %s
                Valor R$: %s
                Status: %s 
                """.formatted(pagamento.getPedidoId(),
                pagamento.getId(),
                pagamento.getNome(),
                pagamento.getValor(),
                pagamento.getStatus());

        System.out.println(mensagem);
    }
}
