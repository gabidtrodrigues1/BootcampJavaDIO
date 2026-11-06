package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.model.StatusPedido;

public class ValidadorDadosCliente extends ValidadorPedido {

    @Override
    protected String executarValidacao(Pedido pedido) {
        if (pedido.getCliente() == null) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Cliente não informado";
        }

        if (pedido.getCliente().getNome() == null || pedido.getCliente().getNome().trim().isEmpty()) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Nome do cliente não informado";
        }

        if (pedido.getCliente().getCpf() == null || pedido.getCliente().getCpf().trim().isEmpty()) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: CPF do cliente não informado";
        }

        if (!pedido.getCliente().isAtivo()) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Cliente inativo";
        }

        return "Dados do cliente validados com sucesso";
    }
}