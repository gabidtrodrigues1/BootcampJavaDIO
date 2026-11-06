package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.model.StatusPedido;
import com.exemplo.pagamentos.singleton.GerenciadorConfiguracao;

public class ValidadorLimite extends ValidadorPedido {

    @Override
    protected String executarValidacao(Pedido pedido) {
        GerenciadorConfiguracao config = GerenciadorConfiguracao.getInstancia();

        if (pedido.getValorTotal() == null) {
            pedido.calcularTotal();
        }

        if (pedido.getValorTotal().compareTo(config.getLimiteMaximoPedido()) > 0) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Pedido excede o limite máximo de R$ " + config.getLimiteMaximoPedido();
        }

        return "Limite validado com sucesso (até R$ " + config.getLimiteMaximoPedido() + ")";
    }
}