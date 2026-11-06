package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public class PagamentoBoleto implements EstrategiaPagamento {

    private static final BigDecimal TAXA_FIXA = new BigDecimal("3.50");

    @Override
    public String pagar(Pedido pedido) {
        return "Pagamento via Boleto processado com sucesso para o pedido " + pedido.getId();
    }

    @Override
    public String getNome() {
        return "Boleto";
    }

    @Override
    public BigDecimal calcularTaxa(Pedido pedido) {
        return TAXA_FIXA;
    }
}