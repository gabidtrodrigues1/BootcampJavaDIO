package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.Pedido;

import java.time.LocalDateTime;

public class RegistraLogObserver implements ObservadorPagamento {

    @Override
    public String notificar(Pedido pedido) {
        return "LOG [" + LocalDateTime.now() + "] Pedido " + pedido.getId()
                + " | Status: " + pedido.getStatus()
                + " | Valor: R$ " + pedido.getValorTotal()
                + " | Pagamento: " + pedido.getTipoPagamento();
    }
}