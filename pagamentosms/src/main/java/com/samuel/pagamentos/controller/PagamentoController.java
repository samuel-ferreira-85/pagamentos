package com.samuel.pagamentos.controller;

import com.samuel.pagamentos.dto.PagamentoDto;
import com.samuel.pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private PagamentoService pagamentoService;
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable pageable) {
        return pagamentoService.obterTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id) {
        var pagamentoDto = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoDto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody PagamentoDto pagamentoDto,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        var pagamentoCriado = pagamentoService.criarPagamento(pagamentoDto);
        URI uri = uriComponentsBuilder.path("/pagamentos/{id}")
                .buildAndExpand(pagamentoCriado.getId()).toUri();

//        var message = new Message(("Pagamento criado com id " + pagamentoCriado.getId()).getBytes());
//        rabbitTemplate.convertAndSend("pagamento-concluido", pagamentoCriado);
        rabbitTemplate.convertAndSend("pagamentos.ex", "", pagamentoCriado);

        return ResponseEntity.created(uri).body(pagamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable Long id,
                                                  @RequestBody @Valid PagamentoDto pagamentoDto) {
        var pagamentoAtualizado = pagamentoService.atualizarPagamento(id, pagamentoDto);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPedido(@PathVariable @NotNull Long id) {
        pagamentoService.confirmarPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e) {
        pagamentoService.alteraStatus(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
