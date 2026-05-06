package com.financeiro.api_financeiro.transacao.service;

import com.financeiro.api_financeiro.transacao.dto.TransacaoRequest;
import com.financeiro.api_financeiro.transacao.dto.TransacaoResponse;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;
import com.financeiro.api_financeiro.transacao.repository.TransacaoRepository;
import com.financeiro.api_financeiro.usuario.model.Usuario;
import com.financeiro.api_financeiro.usuario.service.UsuarioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {
    private final TransacaoRepository repository;
    private final UsuarioService usuarioService;



    public TransacaoService(TransacaoRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public List<TransacaoResponse> listarTodas() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(email);
        return repository.findByUsuario(usuario)
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

    public TransacaoResponse salvar(TransacaoRequest request) {
        Transacao transacao = new Transacao();

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(email);
        transacao.setUsuario(usuario);
        transacao.setDescricaoTransacao(request.descricao());
        transacao.setValorTransacao(request.valor());
        transacao.setDataTransacao(request.data().atStartOfDay());
        transacao.setTipoTransacao(request.tipoTransacao());
        return new TransacaoResponse(repository.save(transacao));
    }

    public TransacaoResponse atualizar(Integer id, TransacaoRequest request) {

        Transacao item = repository.findById(id).orElseThrow(() -> new RuntimeException("Transacao nao encontrada"));

        //DESCRICAO
        if (request.descricao() != null) {
            item.setDescricaoTransacao(request.descricao());
        }
        //VALOR
        if (request.valor() != null) {
            item.setValorTransacao(request.valor());
        }

        //TIPO
        if (request.tipoTransacao() != null) {
            item.setTipoTransacao(request.tipoTransacao());
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
