package com.exemplo.pagamentos.factory;

import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.strategy.EstrategiaPagamento;
import com.exemplo.pagamentos.strategy.PagamentoPix;
import com.exemplo.pagamentos.strategy.PagamentoCartaoCredito;
import com.exemplo.pagamentos.strategy.PagamentoBoleto;
import com.exemplo.pagamentos.strategy.EstrategiaDesconto;
import com.exemplo.pagamentos.strategy.DescontoPix;
import com.exemplo.pagamentos.strategy.DescontoBoleto;
import com.exemplo.pagamentos.strategy.SemDesconto;

import java.util.HashMap;
import java.util.Map;

public class PagamentoFactory {

    private final Map<String, EstrategiaPagamento> estrategiasPagamento = new HashMap<>();
    private final Map<String, EstrategiaDesconto> estrategiasDesconto = new HashMap<>();

    public PagamentoFactory() {
        estrategiasPagamento.put("PIX", new PagamentoPix());
        estrategiasPagamento.put("CARTAO_CREDITO", new PagamentoCartaoCredito());
        estrategiasPagamento.put("BOLETO", new PagamentoBoleto());

        estrategiasDesconto.put("PIX", new DescontoPix());
        estrategiasDesconto.put("BOLETO", new DescontoBoleto());
        estrategiasDesconto.put("CARTAO_CREDITO", new SemDesconto());
    }

    public EstrategiaPagamento obterEstrategiaPagamento(Pedido pedido) {
        String chave = pedido.getTipoPagamento().name();
        EstrategiaPagamento estrategia = estrategiasPagamento.get(chave);
        if (estrategia == null) {
            throw new IllegalArgumentException("Tipo de pagamento não suportado: " + chave);
        }
        return estrategia;
    }

    public EstrategiaDesconto obterEstrategiaDesconto(Pedido pedido) {
        String chave = pedido.getTipoPagamento().name();
        return estrategiasDesconto.getOrDefault(chave, new SemDesconto());
    }
}