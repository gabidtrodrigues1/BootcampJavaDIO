package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public class DescontoPix implements EstrategiaDesconto {

    private static final BigDecimal PERCENTUAL = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal calcular(Pedido pedido) {
        return pedido.getValorTotal()
                .multiply(PERCENTUAL)
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "5% de desconto para pagamento via PIX";
    }
}