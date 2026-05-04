package com.financeiro.api_financeiro.transacao.controller;

import com.financeiro.api_financeiro.transacao.dto.TransacaoResponse;
import com.financeiro.api_financeiro.transacao.model.TipoTransacao;
import com.financeiro.api_financeiro.transacao.model.Transacao;
import com.financeiro.api_financeiro.transacao.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }



    @GetMapping("/{id}")
    public TransacaoResponse transacoesPorId(@PathVariable Integer id) {
        return transacaoService.buscarPorId(id);
    }

    @PostMapping
    public TransacaoResponse salvarItem(@RequestBody Transacao transacao){
        return transacaoService.salvar(transacao);
    }

    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Integer id){
        transacaoService.deletarPorId(id);
    }

    @PatchMapping("/{id}")
    public TransacaoResponse atualizarItem(
            @PathVariable Integer id,
            @RequestBody Transacao transacao){
        return transacaoService.atualizar(id, transacao);
    }

    @GetMapping
    public List<TransacaoResponse> transacoes(@RequestParam(required = false) TipoTransacao tipoTransacao){
        if(tipoTransacao == null){
            return transacaoService.listarTodas();
        }
        return transacaoService.filtrarPorTipo(tipoTransacao) ;
    }

    @GetMapping("/consultarSaldo")
    public BigDecimal consultarSaldo(){
        return transacaoService.calcularSaldo();
    }



}
