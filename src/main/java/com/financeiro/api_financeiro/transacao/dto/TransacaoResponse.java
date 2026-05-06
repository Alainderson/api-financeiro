package com.financeiro.api_financeiro.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(@JsonProperty("id")
                                Integer id,

                                @JsonProperty("nome")
                                String nomeTransacao,

                                @JsonProperty("descricao")
                                String descricaoTransacao,

                                @JsonProperty("valor")
                                BigDecimal valorTransacao,

                                @JsonProperty("tipo")
                                TipoTransacao tipoTransacao,

                                @JsonProperty("data")
                                LocalDateTime dataTransacao) {

    public TransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getUsuario().getNome(),
                transacao.getDescricaoTransacao(),
                transacao.getValorTransacao(),
                transacao.getTipoTransacao(),
                transacao.getDataTransacao());
    }

}

