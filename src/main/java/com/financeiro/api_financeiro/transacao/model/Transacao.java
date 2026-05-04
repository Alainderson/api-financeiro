package com.financeiro.api_financeiro.transacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataTransacao;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;
    private BigDecimal valorTransacao;
    private String descricaoTransacao;


}
