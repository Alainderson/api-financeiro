package com.financeiro.api_financeiro.transacao.service;

import com.financeiro.api_financeiro.transacao.dto.TransacaoResponse;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;
import com.financeiro.api_financeiro.transacao.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {
    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<TransacaoResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(TransacaoResponse::new)
                .toList();
    }

    public TransacaoResponse buscarPorId(Integer id) {
        Transacao item = repository.findById(id).orElseThrow(() -> new RuntimeException("Transacao nao encontrada"));
        return new TransacaoResponse(item);
    }

    public void deletarPorId(Integer id) {
        repository.deleteById(id);
    }

    public TransacaoResponse salvar(Transacao transacao) {
        return new TransacaoResponse(repository.save(transacao));
    }

    public TransacaoResponse atualizar(Integer id, Transacao novaTransacao) {

        Transacao item = repository.findById(id).orElseThrow(() -> new RuntimeException("Transacao nao encontrada"));

        //DESCRICAO
        if (novaTransacao.getDescricaoTransacao() != null) {
            item.setDescricaoTransacao(novaTransacao.getDescricaoTransacao());
        }
        //VALOR
        if (novaTransacao.getValorTransacao() != null) {
            item.setValorTransacao(novaTransacao.getValorTransacao());
        }

        //TIPO
        if (novaTransacao.getTipoTransacao() != null) {
            item.setTipoTransacao(novaTransacao.getTipoTransacao());
        }


        return new TransacaoResponse(repository.save(item));


    }

    public List<TransacaoResponse> filtrarPorTipo(TipoTransacao tipoTransacao) {
        return repository.findByTipoTransacao(tipoTransacao)
                .stream()
                .map(TransacaoResponse::new)
                .toList();
    }

    public BigDecimal calcularSaldo() {

        BigDecimal receita = repository.findByTipoTransacao(TipoTransacao.RECEITA)
                .stream()
                .map(t -> t.getValorTransacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal despesa = repository.findByTipoTransacao(TipoTransacao.DESPESA)
                .stream()
                .map(t -> t.getValorTransacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return receita.subtract(despesa);
    }
}
