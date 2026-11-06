package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public class SemDesconto implements EstrategiaDesconto {

    @Override
    public BigDecimal calcular(Pedido pedido) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getDescricao() {
        return "Sem desconto aplicável";
    }
}