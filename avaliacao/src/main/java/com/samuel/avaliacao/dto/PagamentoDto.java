package com.samuel.avaliacao.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PagamentoDto {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private String numero;
    private String expiracao;
    private String codigo;
    private StatusPagamento status;
    private Long pedidoId;
    private Long formaPagamentoId;
}
