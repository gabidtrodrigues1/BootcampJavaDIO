package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public class PagamentoPix implements EstrategiaPagamento {

    @Override
    public String pagar(Pedido pedido) {
        return "Pagamento via PIX processado com sucesso para o pedido " + pedido.getId();
    }

    @Override
    public String getNome() {
        return "PIX";
    }

    @Override
    public BigDecimal calcularTaxa(Pedido pedido) {
        return BigDecimal.ZERO;
    }
}