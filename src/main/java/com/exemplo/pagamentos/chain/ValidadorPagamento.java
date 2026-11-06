package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.model.StatusPedido;

public class ValidadorPagamento extends ValidadorPedido {

    @Override
    protected String executarValidacao(Pedido pedido) {
        if (pedido.getTipoPagamento() == null) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Tipo de pagamento não informado";
        }

        return "Tipo de pagamento '" + pedido.getTipoPagamento() + "' validado com sucesso";
    }
}