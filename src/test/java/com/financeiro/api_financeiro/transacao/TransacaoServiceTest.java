package com.financeiro.api_financeiro.transacao;

import com.financeiro.api_financeiro.transacao.dto.TransacaoResponse;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;
import com.financeiro.api_financeiro.transacao.repository.TransacaoRepository;
import com.financeiro.api_financeiro.transacao.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private TransacaoService service;

    @Test
    void deveListarTodasAsTransacoes() {
        // dado
        Transacao t1 = new Transacao();
        t1.setDescricaoTransacao("Aluguel");
        t1.setValorTransacao(new BigDecimal("1500.00"));
        t1.setTipoTransacao(TipoTransacao.DESPESA);
        t1.setDataTransacao(LocalDateTime.now());

        when(repository.findAll()).thenReturn(List.of(t1));

        // quando
        List<TransacaoResponse> resultado = service.listarTodas();

        // então
        assertEquals(1, resultado.size());
        assertEquals("Aluguel", resultado.get(0).descricaoTransacao());
    }

    @Test
    void deveBuscarTransacaoPorId(){

        // dado
        Transacao t1 = new Transacao();
        t1.setDescricaoTransacao("Diarista");
        t1.setValorTransacao(new BigDecimal("1500.00"));
        t1.setTipoTransacao(TipoTransacao.DESPESA);
        t1.setDataTransacao(LocalDateTime.now());

        when(repository.findById(t1.getId())).thenReturn(Optional.of(t1));

        //quando
        TransacaoResponse item = service.buscarPorId(t1.getId());

        //entao
        assertEquals("Diarista", item.descricaoTransacao());
        assertEquals(TipoTransacao.DESPESA, item.tipoTransacao());

    }

    @Test
    void deveRetornarErroCorreto() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(99));
    }



}