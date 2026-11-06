package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.Pedido;

public class IntegraErpObserver implements ObservadorPagamento {

    @Override
    public String notificar(Pedido pedido) {
        return "ERP integrado: pedido " + pedido.getId()
                + " enviado para o sistema de faturamento.";
    }
}