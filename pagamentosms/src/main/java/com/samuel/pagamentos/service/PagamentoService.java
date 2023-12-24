package com.samuel.pagamentos.service;

import com.samuel.pagamentos.dto.PagamentoDto;
import com.samuel.pagamentos.model.Pagamento;
import com.samuel.pagamentos.model.Status;
import com.samuel.pagamentos.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;
    private ModelMapper modelMapper;

    public Page<PagamentoDto> obterTodos(Pageable pageable) {
        return pagamentoRepository.findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDto.class));
    }

    public PagamentoDto obterPorId(Long id) {
        var pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto pagamentoDto) {
        var pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    @Transactional
    public PagamentoDto atualizarPagamento(Long id, PagamentoDto pagamentoDto) {
        var pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);
        log.info("Pagamento = {}", pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
