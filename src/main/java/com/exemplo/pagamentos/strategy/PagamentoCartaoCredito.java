package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagamentoCartaoCredito implements EstrategiaPagamento {

    private static final BigDecimal TAXA = BigDecimal.valueOf(0.035);

    @Override
    public String pagar(Pedido pedido) {
        return "Pagamento via Cartão de Crédito processado com sucesso para o pedido " + pedido.getId();
    }

    @Override
    public String getNome() {
        return "Cartão de Crédito";
    }

    @Override
    public BigDecimal calcularTaxa(Pedido pedido) {
        return pedido.getValorTotal()
                .multiply(TAXA)
                .setScale(2, RoundingMode.HALF_UP);
    }
}