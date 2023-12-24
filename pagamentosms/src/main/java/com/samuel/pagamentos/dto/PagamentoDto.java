package com.samuel.pagamentos.dto;

import com.samuel.pagamentos.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private String nome;
    private BigDecimal valor;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long pedidoId;
    private Long formaPagamentoId;
}
