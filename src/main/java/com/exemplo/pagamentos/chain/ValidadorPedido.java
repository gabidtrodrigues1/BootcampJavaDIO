package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.model.StatusPedido;

public abstract class ValidadorPedido {

    protected ValidadorPedido proximo;

    public ValidadorPedido encadear(ValidadorPedido proximo) {
        this.proximo = proximo;
        return proximo;
    }

    public String validar(Pedido pedido) {
        String resultado = executarValidacao(pedido);

        if (pedido.getStatus() == StatusPedido.REJEITADO) {
            return resultado;
        }

        if (proximo != null) {
            String passo = proximo.validar(pedido);
            if (passo != null && !passo.isEmpty()) {
                resultado += "; " + passo;
            }
        }

        return resultado;
    }

    protected abstract String executarValidacao(Pedido pedido);
}