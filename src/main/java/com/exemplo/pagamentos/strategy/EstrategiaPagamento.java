package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.Pedido;

import java.math.BigDecimal;

public interface EstrategiaPagamento {

    String pagar(Pedido pedido);

    String getNome();

    BigDecimal calcularTaxa(Pedido pedido);
}