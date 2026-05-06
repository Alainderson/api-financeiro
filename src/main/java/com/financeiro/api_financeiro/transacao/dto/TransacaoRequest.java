package com.financeiro.api_financeiro.transacao.dto;

import com.financeiro.api_financeiro.transacao.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequest(
        TipoTransacao tipoTransacao,
        String descricao,
        BigDecimal valor,
        LocalDate data
) {
}
