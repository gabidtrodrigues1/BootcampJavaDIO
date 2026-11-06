package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.Pedido;

public class EnviaEmailObserver implements ObservadorPagamento {

    @Override
    public String notificar(Pedido pedido) {
        return "E-mail enviado para " + pedido.getCliente().getEmail()
                + " - Pedido " + pedido.getId() + " processado com sucesso.";
    }
}