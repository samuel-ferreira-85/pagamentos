package com.samuel.pedidos.amqp;

import com.samuel.pedidos.dto.PagamentoDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentosListener {

    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void listenerMessage(PagamentoDto pagamentoDto) {
        var mensagem = """
                Cliente: %s
                Dados do pagamento: %s
                Número do pedido: %s
                Valor R$: %s
                Status: %s
                """.formatted(
                        pagamentoDto.getNome(),
                        pagamentoDto.getId(),
                        pagamentoDto.getPedidoId(),
                        pagamentoDto.getValor(),
                        pagamentoDto.getStatus());
        System.out.println("Recebi a mensagem : \n" + mensagem);
    }
}
