package com.financeiro.api_financeiro.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(@JsonProperty("descricao")
                                String descricaoTransacao,

                                @JsonProperty("valor")
                                BigDecimal valorTransacao,

                                @JsonProperty("tipo")
                                TipoTransacao tipoTransacao,

                                @JsonProperty("data")
                                LocalDateTime dataTransacao) {

    public TransacaoResponse(Transacao transacao) {
        this(transacao.getDescricaoTransacao(),
                transacao.getValorTransacao(),
                transacao.getTipoTransacao(),
                transacao.getDataTransacao());
    }

}

