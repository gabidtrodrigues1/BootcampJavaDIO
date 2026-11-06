package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public class DescontoBoleto implements EstrategiaDesconto {

    private static final BigDecimal DESCONTO_FIXO = BigDecimal.valueOf(2.00);

    @Override
    public BigDecimal calcular(Pedido pedido) {
        return DESCONTO_FIXO;
    }

    @Override
    public String getDescricao() {
        return "R$ 2,00 de desconto fixo para pagamento via Boleto";
    }
}