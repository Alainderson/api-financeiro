package com.financeiro.api_financeiro.transacao.repository;

import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;
import com.financeiro.api_financeiro.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    List<Transacao> findByTipoTransacao(TipoTransacao tipoTransacao);

    List<Transacao> findByUsuario(Usuario usuario);
}