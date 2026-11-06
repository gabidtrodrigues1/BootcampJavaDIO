package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public interface EstrategiaDesconto {

    BigDecimal calcular(Pedido pedido);

    String getDescricao();
}