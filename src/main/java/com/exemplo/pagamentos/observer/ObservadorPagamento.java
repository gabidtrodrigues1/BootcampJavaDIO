package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.Pedido;

public interface ObservadorPagamento {

    String notificar(Pedido pedido);
}